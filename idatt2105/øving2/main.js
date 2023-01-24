const app = Vue.createApp({
    data() {
        return {
            result: "",
            equation: "",
            log: ["asdasaksjkasjhjkashdasd"],
            logToggle: false
        }
    },
    methods: {
        numberInput(id) {
            this.equation += id.toString();
            this.currentNums += id.toString();
            console.log(id)
        },
        add(){
            this.equation += " + ";
        },
        subtract(){
            this.equation += " - ";
        },
        multiply(){
            this.equation += " * ";
        },
        divide(){
            this.equation += " / ";
        },
        equals(){
            this.result = eval(this.equation)
            this.log.push(this.equation + " = " + eval(this.equation));
        },
        clear(){
            this.result = "";
            this.equation = "";
        },
        backspace() {
            this.equation = this.equation.slice(0, -1);
        },
        logToggleButton() {
            this.logToggle = !this.logToggle;
        }


    }
})
