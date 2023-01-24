const app = Vue.createApp({
    data() {
        return {
            result: 0,
            validOperators: ["-","+","/","*"],
            equation: "111 + 222",
            test: ""
        }
    },
    methods: {
        numberInput(id) {
            this.equation += id.toString();
            console.log(this.equation[-1])
        },
    }
})
