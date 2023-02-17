import{_ as c,C as h,o,c as l,a as n,w as r,v as u,b as _,t as m,d,p as g,e as f,f as b}from"./index-6923a9c0.js";const M={data(){return{inputName:"",inputEmail:"",inputMessage:"",status:null,isButtonDisabled:!0,errorMsg:""}},methods:{submitForm(){this.$store.dispatch("createMessage",{name:this.inputName,email:this.inputEmail,message:this.inputMessage}),this.status=this.getStatus(),this.resetFields()},async getStatus(){var t="";try{let e=await h.getStatus();console.log(e.data.response),t=e.data.response,this.status=t,this.disableBtn()}catch(e){console.error(e)}},resetMessage(){this.status=null},evalInput(){this.resetMessage(),this.inputName===""||this.inputEmail===""||this.inputMessage===""?(this.disableBtn(),this.errorMsg="An input is empty"):this.inputEmail.match(/^\S+@\S+\.\S+$/)?this.handleName():(this.disableBtn(),this.errorMsg="Incorrect e-mail format! Include all components: 'username@domainname.extension'")},handleName(){let t=!0,e=this.inputName.split(" ",this.inputName.length);for(let i=0;i<e.length;i++)if(e[i][0]!=" "&&e[i][0]!==e[i][0].toUpperCase()){t=!1;break}t?(this.enableBtn(),this.errorMsg=""):(this.disableBtn(),this.errorMsg="Each name component should start with a capitalized letter!")},disableBtn(){this.isButtonDisabled=!0},enableBtn(){this.isButtonDisabled=!1},resetFields(){this.inputName="",this.inputEmail="",this.inputMessage=""}},mounted(){this.disableBtn()},watch:{inputName(){this.evalInput()},inputEmail(){this.evalInput()},inputMessage(){this.evalInput()}}},v=t=>(g("data-v-6f07536d"),t=t(),f(),t),B={class:"contactForm"},I=v(()=>n("h1",null,"Give feedback",-1)),N=["disabled"],S={key:0,id:"excpMessage"},y={key:1,id:"statusP"};function x(t,e,i,V,s,p){return o(),l("div",B,[I,n("form",{onSubmit:e[3]||(e[3]=_((...a)=>p.submitForm&&p.submitForm(...a),["prevent"]))},[r(n("input",{class:"formInput",type:"text",id:"nameInput",name:"name","onUpdate:modelValue":e[0]||(e[0]=a=>s.inputName=a),placeholder:"Name..."},null,512),[[u,s.inputName]]),r(n("input",{class:"formInput",type:"email",id:"emailInput",name:"email","onUpdate:modelValue":e[1]||(e[1]=a=>s.inputEmail=a),placeholder:"Email..."},null,512),[[u,s.inputEmail]]),r(n("textarea",{class:"formInput",name:"message",id:"messageInput","onUpdate:modelValue":e[2]||(e[2]=a=>s.inputMessage=a),placeholder:"Message..."},null,512),[[u,s.inputMessage]]),n("input",{type:"submit",id:"submitButton",value:"Send",disabled:s.isButtonDisabled},null,8,N)],32),s.errorMsg!=""?(o(),l("p",S,m(s.errorMsg),1)):d("",!0),s.status?(o(),l("p",y,m(s.status),1)):d("",!0)])}const E=c(M,[["render",x],["__scopeId","data-v-6f07536d"]]);const w={class:"contact"},F={__name:"ContactView",setup(t){return(e,i)=>(o(),l("div",w,[b(E)]))}};export{F as default};
