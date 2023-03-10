<script setup>
import CollectionService from '@/services/CollectionService'
defineProps({
  msg: {
    type: String,
    required: true
  }
})
</script>

<script>
export default {
  data() {
        return {
            result: "",
            equation: "",
            log: [""],
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
        },
        calcApi() {
          axios.get("http://localhost:8080/greeting", { params: { name: this.equation } })
          .then(response => {
            this.result = response.data;
            this.log.push(`${this.equation} = ${response.data}`);
          })
          .catch(error => console.error(error));
      }

    }
};
</script>

<template>
  <div class="container">
     
     <div class="preview">
       <p>{{ equation }}</p>
       <p id="result"> {{ result }}</p>
     </div>
     <div class="parent">
       <div v-on:click="numberInput(7)">7</div>
       <div v-on:click="numberInput(8)">8</div>
       <div v-on:click="numberInput(9)">9</div>
       <div @click="add()">+</div>
       <div v-on:click="numberInput(4)">4</div>
       <div v-on:click="numberInput(5)">5</div>
       <div v-on:click="numberInput(6)">6</div>
       <div @click="subtract()">-</div>
       <div v-on:click="numberInput(1)">1</div>
       <div v-on:click="numberInput(2)">2</div>
       <div v-on:click="numberInput(3)">3</div>
       <div @click="divide()">/</div>
       <div v-on:click="numberInput(0)">0</div>
       <div @click="calcApi()">=</div>
       <div v-on:click="numberInput('.')">.</div>
       <div @click="multiply()">*</div>
     </div>
     <h4 id="logo">Calculator Rex</h4>

   </div>
   
   <div class="settings">
     <div @click="clear()">Clear</div>
     <div @click="backspace()">Backspace</div>
     <div @click="logToggleButton()">Log</div>
   </div>
   
   <div v-show="logToggle" style>
     <ul>
       <li v-for="logitem in log">{{ logitem }}</li>
     </ul>
   </div>
</template>

<style scoped>
  .preview {
      position: relative;
      height: 100px;
      border: 2px solid var(--color-text);
      margin-bottom: 10px;
      box-shadow: inset 4px 2px 0 0 var(--color-text);
      padding-left: 0.5em;
    }

  .preview > p {
    display: inline-block;
  }

    .preview > #result {
        position: absolute;
        right: 0.5em;
        bottom: 0.5em;
    }
  
  .container {
    display: block;
    position: relative;
    background-color: rgba(255, 255, 255, 0.35);
    padding: 3rem 4rem;
    border: 2px solid var(--color-text);
    box-shadow: 7px 6px 0 0 var(--color-text);
    border-radius: var(--border-radius);
    max-width: 500px;
    transition: transform 0.35s;
  }


  .parent {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(4, 1fr);
  grid-column-gap: 10px;
  grid-row-gap: 10px;
  color: var(--color-text);
  }
      


  .parent > div {
      text-align: center;
      border: 2px solid var(--color-text);
      padding: 2em;
  }

  .parent > div:hover{
      color: var(--color-accent);
      background-color: var(--color-text);
  }

  .settings {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-column-gap: 10px;
    margin-top: 2em;
  }

  .settings > div {
    text-align: center;
    border: 2px solid var(--color-text);
    padding: 1em;
  }

  .settings > div:hover{
      color: var(--color-accent);
      background-color: var(--color-text);
  }

  #logo {
    position: absolute;
    bottom: 5px;
    right: 5px;
    margin: 0;
    padding: 0;
    font-size: 0.7em;
    color: var(--color-text);
  }
</style>
