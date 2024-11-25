
// --------------CONFIRMACION DE REGISTROS
$(document).ready(function(){
    $('.confirmRegAlert').click(function(){
        Swal.fire({
            title: "Confirmar registro?",
            text: "No podra eliminarlo, solo cambiar su estado",
            icon: "info",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Si Registrarlo!"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Registrado!",
                    text: "Se realizo correctamente!.",
                    icon: "success"
                });
            }
        });
    });
});

// --------------CONFIRMACION DE ACTUALIZACIONES
$(document).ready(function(){
    $('.confirmUpdAlert').click(function(){
        Swal.fire({
            title: "Confirmar actualizacion?",
            text: "Puede volver a editar los datos despues",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Si Actualizar!"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Actualizado!",
                    text: "Se actualizo correctamente!.",
                    icon: "success"
                });
            }
        });
    });
});

// --------------CONFIRMACION DE CAMBIO DE ESTADO USERS---------------------------
//--------------DESHABILITAR
$(document).ready(function(){
    $('.confirmDesStatusAlert').click(function(){
        let userName = $(this).data('user'); 
        Swal.fire({
            title: `¿Deshabilitar a ${userName}?`,
            text: "Puede cambiar el estado después",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí, Deshabilitar!"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Deshabilitado!",
                    text: "Se deshabilitó correctamente.",
                    icon: "success"
                });
            }
        });
    });
});

//--------------HABILITAR
$(document).ready(function(){
    $('.confirmHabStatusAlert').click(function(){
        let userName = $(this).data('user');

        Swal.fire({
            title: `¿Habilitar a ${userName}?`,
            text: "Puede cambiar el estado después",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí, Habilitar!"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Habilitado!",
                    text: "Se Habilitó correctamente.",
                    icon: "success"
                });
            }
        });
    });
});

