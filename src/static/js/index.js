$.ajaxSetup({
    headers: {
        'Accept': 'application/json'
    }
});


const API = {
    getTickets: async function(id) {
        return JSON.parse(await $.post("api/index.php", {f: "getTickets", "id": id}).then(e=>e));
    },
    addTicket: async function(id, userName,amount) {
        return JSON.parse(await $.post(`api/index.php`, {f: "createTickets","id": id,"userName": userName,"numberOfTickets": amount}).then(e=>e));
    },
    drawTicket: async function(id) {
        return JSON.parse(await $.post("api/index.php", {f: "drawTicket", "id": id}).then(e=>e));
    },
    getLottery: async function (id) {
        return JSON.parse(await $.post("api/index.php", {f: "getLottery", "id": id}).then(e=>e));
    },
    createLottery: async function (phone, price) {
        return JSON.parse(await $.post( "api/index.php", {f: "createLottery", ticketCost: price, phoneNumber: phone}).then(e=>e));
    }
};

var app = new Vue({
    el: '#app',
    data: {
        admin: false,
        state: "start",
        wColor: 'f-red',
        lottery: {
            id: "",
            price: "",
            phone: ""
        },
        newTicket: {
            name: "",
            amount: ""
        },
        drawing: {
            winner: "sdfsd"
        },
        api: {
            tickets: []
        }
    },
    computed: {
        tickets: function(){
            return this.api.tickets.reduce((a,b)=> ((a[b.userName] = a[b.userName] ? ++a[b.userName] : 1),a) ,{})
        }
    },
    watch: {},
    methods: {
        visualizeDrawing: function() {
            let winner = API.getWinner();
        },
        goToStart: function () {
            this.state = "start";

        },
        joinLottery: function(id){
            console.log("joinLottery");
            API.getLottery(id).then(data=>{
                this.lottery.id = data.lottery.id;
                this.lottery.price = data.lottery.ticketCost;
                this.lottery.phone = data.lottery.phoneNumber;
                this.lottery.createdDate = data.lottery.createdDate;
                this.goToOverview();
            });

        },
        goToOverview: function () {
            console.log("go to overview")

            this.loadTickets();
            this.state = "overview";

        },
        goToDrawing: function () {
            this.state = "drawing";
        },
        addTicket: function () {
            API.addTicket(this.lottery.id, this.newTicket.userName, this.newTicket.amount).then(()=>this.loadTickets());
            this.$refs.username.focus();
            this.newTicket.userName = "";
            this.newTicket.amount = "";
        },
        startNewLottery: function () {
            API.createLottery(this.lottery.phone,this.lottery.price).then(data=>{
                console.log(data);
                this.admin = true;
                this.joinLottery(data.lottery.id);
            });
        },
        drawTicket: function () {
            let lotteryid = this.lottery.id;
            API.drawTicket(lotteryid).then(winnerWrapper =>{
                const winner = winnerWrapper.ticket;
                API.getTickets(lotteryid).then(data=>{
                    this.wColor = "f-red";
                    let tickets = data.ticketList.filter(e=>e.winner!="0");
                    for (let i = 10; i < 100; i++) {
                        setTimeout(() => {
                            n = ~~(Math.random() * (tickets.length));
                            this.drawing.winner = tickets[n].userName;
                        }, 40000 / i)
                    }
                    setTimeout(()=>{
                        this.drawing.winner = winner.userName;
                        this.wColor = "f-green";
                    },4200);
                });
            });


        },
        deleteTicket: function () {
            //TODO: Call api to delete ticket
        },
        loadTickets(){
            API.getTickets(this.lottery.id).then(data =>this.api.tickets=data.ticketList);
        }
    },
    created: function(){
        let cursor = 0;
        const KONAMI_CODE = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65];
        document.addEventListener('keydown', (e) => {
            cursor = (e.keyCode == KONAMI_CODE[cursor]) ? cursor + 1 : 0;
            if (cursor == KONAMI_CODE.length) this.admin=true;
        });
        setInterval(()=>{
            if(typeof this.lottery.createdDate !== 'undefined'){
                this.loadTickets();
            }
        },5000)
    }
});