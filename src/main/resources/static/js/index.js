var app = new Vue({
    el: '#app',
    data: {
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

        }
    },
    computed: {},
    watch: {},
    methods: {
        goToStart: function () {
            this.state = "start";

        },
        goToOverview: function () {
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
            this.goToOverview();
        },
        drawTicket: function () {
            //TODO: call api to draw ticket
            //TODO: visualize drawing
        },
        deleteTicket: function() {
            //TODO: Call api to delete ticket
        }
    }
});