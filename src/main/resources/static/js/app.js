const Toast = Swal.mixin({
    icon: 'success',
    title: 'Enviado com Sucesso!',
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

document.getElementById('onDevelopment').addEventListener('click', () => {Swal.fire({title: 'Feature em Desenvolvimento', icon: 'warning', timer: 1500, showConfirmButton: false,})});
