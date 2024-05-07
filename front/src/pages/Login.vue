<script>
import {useVuelidate} from "@vuelidate/core";
import {maxLength, minLength, required} from "@vuelidate/validators";

export default {
  data() {
    return {
      v$: useVuelidate(),
      login: '',
      email: '',
      password: ''
    };
  },
  validations() {
    return {
      login: {required, minLength: minLength(3), maxLengthValue: maxLength(30)},
      password: {required, minLength: minLength(6), maxLengthValue: maxLength(30)},
    }
  },
  methods: {
    submitForm() {
      this.v$.$validate()
      if (this.v$.$error) {
        return
      }

      this.$router.push({path: "/"})
    },
    signIn() {

      this.$router.push({path: "/signin"})
    }
  },

  mounted() {

  }
};
</script>

<template>
  <div class="login-scr">
      <div class="logo-card">
        <p id="logo">ЛОГОТИП</p>
        <p class="text"> Мы рады приветствовать вас в нашем инструменте управления задачами!</p>
        <p class="text">Здесь вы сможете эффективно организовывать свою работу, отслеживать процесс выполнения
          задач и сотрудничать с коллегами! Управляйте проектами грамотно с Mantis!</p>
      </div>
      <div class="login-form">
        <div>
          <h2>ВХОД</h2>
          <form @submit.prevent="submitForm">
            <div class="form-group">
              <input placeholder="Логин" type="text" id="login" v-model="login">
              <p class="err-message" v-if="v$.login.$error">! {{ v$.login.$errors[0].$message }}</p>
            </div>
            <div class="form-group">
              <input placeholder="Пароль" type="text" id="password" v-model="password">
              <p class="err-message" v-if="v$.password.$error">! {{ v$.password.$errors[0].$message }}</p>
            </div >
            <v-btn
                class="submit-button"
                type="submit"
                @click=""
            >
              Войти
            </v-btn>
            <div>
              <p class="text-but" @click="signIn">Регистрация</p>
            </div>
          </form>
        </div>
      </div>
  </div>

</template>

<style scoped>
.login-scr {
  width: 50%;
  height: 500px;
  min-width: 600px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 50px;
  border: 1px solid #C0CFD3;
  background: #F7FBFC;
  overflow: hidden;
  border-spacing: 0;
  display: flex;
}

.logo-card {
  width: 50%;
  background-image: linear-gradient(rgba(16, 75, 119, 1), rgba(23, 67, 101, .4)), url("@/assets/bg.jpg");
  background-position: center;
  background-size: cover;
  vertical-align: top;
  justify-content: center;
  align-items: center;
  padding: 50px 40px;
  display: flex;
  flex-direction: column;
}

.login-form {
  width: 50%;
  padding: 80px 20px 20px;
}

.err-message {
  margin-right: 20px;
  margin-left: 20px;
  font-size: 10px;
  color: red;
}

.text {
  text-align: center;
  color: #FFFFFF;
  font-size: 14px;
  margin-bottom: 10px;
}

.submit-button {
  border-radius: 30px;
  padding: 30px;
  width: 200px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 70px auto 10px;
  background: #AFD7F4;
  //background: linear-gradient(to right, #FFFFFF, #AFD7F4);
  //color: #AFD7F4;
}

.form-group {
  margin: 10px 0 10px;
  width: 100%;
}

input {
  width: 100%;
  height: 50px;
  border: 1px solid #C0CFD3;
  border-radius: 50px;
  padding: 10px 10px 10px 20px;
}

#logo {
  font-size: 30px;
  color: #FFFFFF;
  margin-bottom: 30px;

//font-family: "Arial", serif;

}

.text-but {
  text-align: center;
  cursor: pointer;
}

form {
  align-items: center;
  margin-top: 20px;
}

tr {
  overflow: hidden;
}

h2 {
  font-size: 28px;
  text-align: center;
  margin-bottom: 50px;
}

label {
  display: block;
  margin-bottom: 5px;
}
</style>