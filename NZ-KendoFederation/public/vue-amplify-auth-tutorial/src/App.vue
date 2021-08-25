<template>
  <div id="app">
    <div id="nav">
      <div v-if="authState === 'signedin' && user">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/register">Register</router-link> |
      <router-link to="/login">Login</router-link> 
      
      <amplify-greetings :username="user.username" ></amplify-greetings>
    </div>
      <div v-if="authState !== 'signedin'">You are signed out.</div>
      <amplify-authenticator>
        <div v-if="authState === 'signedin' && user">
          <div>Hello, {{ user.username }}</div>
          <textarea>Hello Luke I am your father</textarea>
        </div>
        <amplify-sign-out></amplify-sign-out>
        <amplify-sign-in
          header-text="NZ Kendo Federation Login"
          slot="sign-in"
        ></amplify-sign-in>
        <amplify-sign-up
          header-text="Create a NZ Kendo Federation Account"
          slot="sign-up"
        ></amplify-sign-up>
      </amplify-authenticator>
    </div>
  </div>
</template>


<script>
import { onAuthUIStateChange } from "@aws-amplify/ui-components";

export default {
  name: "AuthStateApp",
  created() {
    this.unsubscribeAuth = onAuthUIStateChange((authState, authData) => {
      this.authState = authState;
      this.user = authData;
    });
  },
  data() {
    return {
      user: undefined,
      authState: undefined,
      unsubscribeAuth: undefined,
      // formFields: [
      //   {
      //     type: 'email',
      //     label: 'Custom Email Label',
      //     placeholder: 'Custom email placeholder',
      //     inputProps: { required: true, autocomplete: 'username' },
      //   },
      //   {
      //     type: 'password',
      //     label: 'I have no clue what this does',
      //     placeholder: 'Not working',
      //     inputProps: { required: true, autocomplete: 'Not working' },
      //   },
       
      // ],
    };
  },
  beforeUnmount() {
    this.unsubscribeAuth();
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

:input {
  margin-right: 10px;
}
</style>
