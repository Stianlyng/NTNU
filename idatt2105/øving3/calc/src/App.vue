<template>
  <nav>
    <router-link to="/">Calculator</router-link> |
    <router-link to="/contact">Feedback</router-link>
  </nav>
  <router-view/>
</template>

<style>
#app {
 
  text-align: center;
  color: #060606;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

nav {
  padding: 30px;
  width: auto;
  height: 12%;
  font-size: 20px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #ffffff;
}
</style>
