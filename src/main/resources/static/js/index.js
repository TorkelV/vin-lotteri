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
        }
    },
    computed: {},
    watch: {},
    methods: {
        goToStart: function(){

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
        startNewLottery: function(){
            this.goToOverview();
        }
    }
});