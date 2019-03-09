$.ajaxSetup({
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});
const API = {
    getTickets: async function(id) {
        return await $.get(`/lotteries/${id}/tickets`);
    },
    addTicket: async function(id, userName,amount) {
        return await $.post(`/lotteries/${id}/tickets`, JSON.stringify({
            "userName": userName,
            "numberOfTickets": amount
        }));
    },
    drawTicket: async function(id) {
        console.log(id);
        return await $.ajax({
            method: "PATCH",
            url: `/lotteries/${id}/tickets`
        })
    },
    getLottery: async function (id) {
        return await $.get( "/lotteries/"+id).then(e=>e);
    },
    createLottery: async function (phone, price) {
        return await $.post( "/lotteries", JSON.stringify({ticketCost: price, phoneNumber: phone}));
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
            API.getLottery(id).then(data=>{
                this.lottery.id = data.lottery.id;
                this.lottery.price = data.lottery.ticketCost;
                this.lottery.phone = data.lottery.phoneNumber;
                this.lottery.createdDate = data.lottery.createdDate;
                this.goToOverview();
            });

        },
        goToOverview: function () {
            this.loadTickets();
            this.state = "overview"
        },
        goToDrawing: function () {
            this.state = "drawing";
        },
        addTicket: function () {
            API.addTicket(this.lottery.id, this.newTicket.userName, this.newTicket.amount).then(()=>this.loadTickets());

            this.newTicket.userName = "";
            this.newTicket.amount = "";
        },
        startNewLottery: function () {
            API.createLottery(this.lottery.phone,this.lottery.price).then(data=>{
                this.admin = true;
                this.joinLottery(data.lottery.id);
            });
        },
        drawTicket: function () {
            let lotteryid = this.lottery.id;
            console.log(lotteryid)
            API.drawTicket(lotteryid).then(winnerWrapper =>{
                const winner = winnerWrapper.ticket;
                API.getTickets(lotteryid).then(data=>{
                    this.wColor = "f-red";
                    let tickets = data.ticketList.filter(e=>!e.winner);
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

        setInterval(()=>{
            if(typeof this.lottery.createdDate !== 'undefined'){
                this.loadTickets();
            }
        },5000)
    }
});