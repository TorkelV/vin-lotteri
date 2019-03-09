var API = {
    getTickets: () => {
        let i = 0;
        return [
            {id: '0', name: 'Torkel', winner: false},
            {id: '1', name: 'Helene', winner: false},
            {id: '2', name: 'Ragnhild', winner: false},
            {id: '', name: 'Ragnhild', winner: false},
            {id: '', name: 'Stian', winner: false},
            {id: '', name: 'Stian', winner: false},
            {id: '', name: 'Norvald', winner: false},
        ].map(e => (e.id = i++, e));
    },
    addTicket: (name,amount) => {

    },
    getLottery: (id) => {
      return {
          id: 123,
          price: 10,
          phone: 94878147
      }
    },
    createLottery: () => {
      return {
          id: 123,
          price: 10,
          phone: 94878147
      }
    }
};

var app = new Vue({
    el: '#app',
    data: {
        admin: false,
        state: "start",
        lottery: {
            id: "",
            price: "",
            phone: ""
        },
        newTicket: {
            username: "",
            amount: ""
        },
        drawing: {
            winner: ""
        },
        api: {
          tickets: {}
        },
        overview: {

        }
    },
    computed: {
        tickets: function(){
            return this.api.tickets.reduce((a,b)=> ((a[b.name] = a[b.name] ? ++a[b.name] : 1),a) ,{})
        }
    },
    watch: {},
    methods: {
        goToStart: function () {
            this.state = "start";

        },
        joinLottery: function(id){
            this.loadTickets();
            this.lottery = API.getLottery(id);
            this.goToOverview();
        },
        goToOverview: function () {
            this.state = "overview"
        },
        goToDrawing: function () {
            this.state = "drawing";
        },
        addTicket: function () {
            API.addTicket(this.newTicket.username,this.newTicket.amount);
            this.newTicket.username = "";
            this.newTicket.amount = "";
        },
        startNewLottery: function () {
            let lottery = API.createLottery(this.lottery.phone,this.lottery.price);
            this.admin = true;
            this.joinLottery(lottery.id);
        },
        drawTicket: function () {
            //TODO: call api to draw ticket
            //TODO: visualize drawing
        },
        deleteTicket: function () {
            //TODO: Call api to delete ticket
        },
        loadTickets(){
            this.api.tickets = API.getTickets();
        }
    },
    created: function(){
    }
});