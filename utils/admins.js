const URL = 'http://localhost:8080';
let adminList = [];

// Obtener todos los administradores con rol 1
const findAllAdmins = async () => {
    try {
        const response = await fetch(`${URL}/api/user`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        });
        const data = await response.json();

        // Filtrar solo administradores (rol 1)
        adminList = (data.data || []).filter(user => user.role && user.role.id === 1);
    } catch (error) {
        console.error('Error al obtener administradores:', error);
    }
};

// Cargar la tabla de administradores
const loadAdminTable = async () => {
    await findAllAdmins();
    const tbody = document.getElementById('tbodyLoad');
    tbody.innerHTML = adminList.length
        ? adminList.map((admin, index) => `
            <tr>
                <td>${index + 1}</td>
                <td>${admin.name}</td>
                <td>${admin.lastname}</td>
                <td>${admin.username}</td>
                <td class="text-center">
                    <button class="btn btn-secondary btn-sm me-2" onclick="viewAdmin(${admin.id})" data-bs-toggle="modal" data-bs-target="#viewAdminModal">
                        <i class="bi bi-eye"></i>
                    </button>
                    <button class="btn btn-success btn-sm me-2" onclick="loadAdmin(${admin.id})" data-bs-toggle="modal" data-bs-target="#editAdminModal">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="deleteAdmin(${admin.id})">
                        <i class="bi bi-person-dash"></i>
                    </button>
                </td>
            </tr>
        `).join("")
        : `<tr><td colspan="5" class="text-center">No hay registros</td></tr>`;
};

// Registrar un nuevo administrador
const saveAdmin = async () => {
    const name = document.getElementById('addAdminName').value.trim();
    const lastname = document.getElementById('addAdminLastname').value.trim();
    const phone = document.getElementById('addAdminPhone').value.trim();
    const email = document.getElementById('addAdminEmail').value.trim();
    const username = document.getElementById('addAdminUsername').value.trim();
    const password = document.getElementById('addAdminPassword').value.trim();

    if (!name || !lastname || !phone || !email || !username || !password) {
        return Swal.fire('Error', 'Por favor, completa todos los campos.', 'error');
    }

    const newAdmin = {
        name,
        lastname,
        phone,
        email,
        username,
        password,
        role: { id: 1 }, // Rol predeterminado de administrador
    };

    try {
        const response = await fetch(`${URL}/api/user`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newAdmin),
        });

        if (response.ok) {
            Swal.fire('Registrado', 'El administrador fue registrado exitosamente.', 'success');
            document.getElementById('addAdminForm').reset(); // Limpia el formulario
            await loadAdminTable();
            bootstrap.Modal.getInstance(document.getElementById('addAdminModal')).hide();
        } else {
            const errorData = await response.json();
            Swal.fire('Error', errorData.message || 'No se pudo registrar el administrador.', 'error');
        }
    } catch (error) {
        Swal.fire('Error', 'Ocurrió un problema al registrar el administrador.', 'error');
    }
};

// Limpia los campos al cerrar el modal de agregar
document.getElementById('addAdminModal').addEventListener('hidden.bs.modal', () => {
    document.getElementById('addAdminForm').reset();
});

// Cargar datos de un administrador en el modal de edición
const loadAdmin = async (id) => {
    try {
        const response = await fetch(`${URL}/api/user/${id}`);
        const data = await response.json();
        const admin = data.data;

        if (admin) {
            document.getElementById('editAdminName').value = admin.name;
            document.getElementById('editAdminLastname').value = admin.lastname;
            document.getElementById('editAdminPhone').value = admin.phone;
            document.getElementById('editAdminEmail').value = admin.email;
            document.getElementById('editAdminName').dataset.adminId = id;
        }
    } catch (error) {
        console.error('Error al cargar administrador:', error);
    }
};

// Actualizar un administrador
const updateAdmin = async () => {
    const id = document.getElementById('editAdminName').dataset.adminId;
    const name = document.getElementById('editAdminName').value.trim();
    const lastname = document.getElementById('editAdminLastname').value.trim();
    const phone = document.getElementById('editAdminPhone').value.trim();
    const email = document.getElementById('editAdminEmail').value.trim();
    const groupId = document.getElementById('editAdminGroup').value;

    if (!name || !lastname || !phone || !email || !groupId) {
        return Swal.fire('Error', 'Por favor, completa todos los campos obligatorios.', 'error');
    }

    const updatedAdmin = {
        id,
        name,
        lastname,
        phone,
        email,
        role: { id: 1 }, // Rol de administrador
        group: { id: parseInt(groupId) },
    };

    try {
        const response = await fetch(`${URL}/api/user`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedAdmin),
        });

        if (response.ok) {
            Swal.fire('Actualizado', 'El administrador fue actualizado exitosamente.', 'success');
            await loadAdminTable();
            bootstrap.Modal.getInstance(document.getElementById('editAdminModal')).hide();
        } else {
            Swal.fire('Error', 'No se pudo actualizar el administrador.', 'error');
        }
    } catch (error) {
        Swal.fire('Error', 'Ocurrió un problema al actualizar el administrador.', 'error');
    }
};


// Eliminar un administrador
const deleteAdmin = async (id) => {
    Swal.fire({
        title: '¿Estás seguro?',
        text: 'No podrás revertir esta acción.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
    }).then(async (result) => {
        if (result.isConfirmed) {
            try {
                const response = await fetch(`${URL}/api/user/${id}`, {
                    method: 'DELETE',
                });

                if (response.ok) {
                    Swal.fire('Eliminado', 'El administrador fue eliminado exitosamente.', 'success');
                    await loadAdminTable();
                } else {
                    Swal.fire('Error', 'No se pudo eliminar el administrador.', 'error');
                }
            } catch (error) {
                Swal.fire('Error', 'No se pudo eliminar el administrador.', 'error');
            }
        }
    });
};

// Ver detalles del administrador
const viewAdmin = async (id) => {
    try {
        const response = await fetch(`${URL}/api/user/${id}`);
        const data = await response.json();
        const admin = data.data;

        const modalBody = document.querySelector('#viewAdminModal .modal-body');
        if (admin) {
            modalBody.innerHTML = `
                <p><strong>Teléfono:</strong> ${admin.phone}</p>
                <p><strong>Correo:</strong> ${admin.email}</p>
            `;
        } else {
            modalBody.innerHTML = '<p>No se encontraron detalles para este administrador.</p>';
        }
    } catch (error) {
        Swal.fire('Error', 'No se pudieron cargar los detalles del administrador.', 'error');
    }
};

// Inicializar eventos
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('addAdminModal').querySelector('.btn-success').addEventListener('click', saveAdmin);
    loadAdminTable();
});
