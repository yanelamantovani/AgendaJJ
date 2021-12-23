const formulario = document.getElementById("formulario");
const inputs = document.querySelectorAll("#formulario input");

const expresiones = {
    name: /^[a-zA-ZÀ-ÿ\s]{2,40}$/, // Letras y espacios, pueden llevar acentos.
    dni: /^\d{7,8}$/, // solo numeros de 7 a 8 numeros
    password: /^.{1,12}$/, // 4 a 12 digitos.
    mail: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
}; //EXPRESIONES REGULARES.

const campos = {
    name: false,
    dni: false,
    password: false,
    mail: false,
};

const validarFormulario = (e) => {
    // el e.target.name es para ver si esta dentro de determinado del input
    switch (e.target.name) {
        case "dni":
            validarCampo(expresiones.dni, e.target, "dni"); //en dni tambien podria ser e.target.name
            break;
        case "name":
            validarCampo(expresiones.name, e.target, "name");
            break;
        case "password":
            validarCampo(expresiones.password, e.target, "password");
            break;
        case "password2":
            validarPassword2();
            break;
        case "mail":
            validarCampo(expresiones.mail, e.target, "mail");
            break;
    }
};

const validarCampo = (expresion, input, campo) => {
    if (expresion.test(input.value)) {
        document
            .getElementById(`grupo${campo}`)
            .classList.remove("formulariogrupoincorrecto");
        document
            .getElementById(`grupo${campo}`)
            .classList.add("formulariogrupocorrecto");
        document.querySelector(`#grupo${campo} i`).classList.add("fa-check-circle");
        document
            .querySelector(`#grupo${campo} i`)
            .classList.remove("fa-times-circle"); //la i es para acceder al icono
        document
            .querySelector(`#grupo${campo} .formularioinputerror`)
            .classList.remove("formularioinputerroractivo");
        campos[campo] = true;
    } else {
        document
            .getElementById(`grupo${campo}`)
            .classList.add("formulariogrupoincorrecto");
        document
            .getElementById(`grupo${campo}`)
            .classList.remove("formulariogrupocorrecto");
        document.querySelector(`#grupo${campo} i`).classList.add("fa-times-circle");
        document
            .querySelector(`#grupo${campo} i`)
            .classList.remove("fa-check-circle");
        document
            .querySelector(`#grupo${campo} .formularioinputerror`)
            .classList.add("formularioinputerroractivo");
        campos[campo] = false;
    }
}; // esta funcion la podemos re utilizar

inputs.forEach((input) => {
    input.addEventListener("keyup", validarFormulario);
    input.addEventListener("blur", validarFormulario);
});

const validarPassword2 = () => {
    const inputpassword1 = document.getElementById("password");
    const inputpassword2 = document.getElementById("password2");

    if (inputpassword1.value !== inputpassword2.value) {
        //error por si ambas no coinciden.
        document
            .getElementById("grupopassword2")
            .classList.add("formulariogrupoincorrecto");
        document
            .getElementById("grupopassword2")
            .classList.remove("formulariogrupocorrecto");
        document
            .querySelector("#grupopassword2 i")
            .classList.add("fa-times-circle");
        document
            .querySelector("#grupopassword2 i")
            .classList.remove("fa-check-circle");
        document
            .querySelector("#grupopassword2 .formularioinputerror")
            .classList.add("formularioinputerroractivo");
        campos["password"] = false;
    } else {
        document
            .getElementById("grupopassword2")
            .classList.remove("formulariogrupoincorrecto");
        document
            .getElementById("grupopassword2")
            .classList.add("formulariogrupocorrecto");
        document
            .querySelector("#grupopassword2 i")
            .classList.remove("fa-times-circle");
        document
            .querySelector("#grupopassword2 i")
            .classList.add("fa-check-circle");
        document
            .querySelector("#grupopassword2 .formularioinputerror")
            .classList.remove("formularioinputerroractivo");
        campos["password"] = true;
    }
};

formulario.addEventListener("submit");

    const terminos = document.getElementById("terminos");
    if (
        campos.dni &&
        campos.name &&
        campos.password &&
        campos.mail &&
        terminos.checked
    ) {
        //formulario.reset();

        document
            .getElementById("formulariomensajeexito")
            .classList.add("formulariomensajeexitoactivo");
        setTimeout(() => {
            document
                .getElementById("formulariomensajeexito")
                .classList.remove("formulariomensajeexitoactivo");
        }, 3000);

        document.querySelectorAll(".formulariogrupocorrecto").forEach((icono) => {
            icono.classList.remove("formulariogrupocorrecto");
        });
    } else {
        formulario.reset();
        document
            .getElementById("formulariomensaje")
            .classList.add("formulariomensajeactivo");
        setTimeout(() => {
            document
                .getElementById("formulariomensaje")
                .classList.remove("formulariomensajeactivo");
        }, 3000);
    }
;