var API = {
    getTickets: () => {
        let i = 0;
        return [
            {id: '0', name: 'Torkel', winner: false},
            {id: '', name: 'Helene', winner: false},
            {id: '', name: 'Ragnhild', winner: false},
            {id: '', name: 'Ragnhild', winner: false},
            {id: '', name: 'Stian', winner: false},
            {id: '', name: 'Stian', winner: false},
            {id: '', name: 'Norvald', winner: false},
        ].map(e => (e.id = i++, e));
    }
};

var app = new Vue({
    el: '#app',
    data: {
        admin: false,
        state: "start",
        lottery: {
            title: "Vin lotteri",
            id: "",
            price: "",
            tlf: ""
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
        goToOverview: function () {
            this.loadTickets();
            this.state = "overview"
        },
        goToDrawing: function () {
            this.state = "drawing";
        },
        addTicket: function () {
            //TODO: call api to add ticket
        },
        startNewLottery: function () {
            //TODO: Call API to create new lottery
            this.admin = true;
            this.goToOverview();
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
    }
});