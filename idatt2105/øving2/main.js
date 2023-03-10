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
        equals() {
            const url = 'http://localhost:8080/greeting?name=' + encodeURIComponent(this.equation);
          
            fetch(url)
              .then(response => response.json())
              .then(data => {
                this.result = data.content;
                this.log.push(this.equation + " = " + data.content);
              })
              .catch(error => {
                console.error('Error:', error);
              });
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
