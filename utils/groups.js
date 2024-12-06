const URL = 'http://localhost:8080';
let groupList = [];

// Obtener todos los grupos
const findAllGroups = async () => {
    try {
        const response = await fetch(`${URL}/api/group`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        });
        const data = await response.json();
        groupList = data.data || [];
    } catch (error) {
        console.error('Error fetching groups:', error);
    }
};

// Cargar la tabla de grupos
const loadTable = async () => {
    await findAllGroups();
    const tbody = document.querySelector("tbody");
    tbody.innerHTML = groupList.length
        ? groupList.map((group, index) => `
            <tr>
                <th>${index + 1}</th>
                <td>${group.name}</td>
                <td>${group.admin ? group.admin.name : 'Aún no cuenta con un administrador'}</td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="viewGroup(${group.id})" data-bs-toggle="modal" data-bs-target="#viewGroupModal">
                        <i class="bi bi-eye"></i>
                    </button>
                    <button class="btn btn-success btn-sm" onclick="loadGroup(${group.id})" data-bs-toggle="modal" data-bs-target="#editGroupModal">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="deleteGroup(${group.id})">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            </tr>
        `).join("")
        : `<tr><td colspan="4" class="text-center">No hay registros</td></tr>`;
};

// Registrar un nuevo grupo
const saveGroup = async () => {
    const name = document.getElementById("addGroupName").value.trim();
    const municipio = document.getElementById("addGroupMunicipio").value.trim();
    const neighborhood = document.getElementById("addGroupNeighborhood").value.trim();
    const adminId = document.getElementById("addAdminGroup").value;

    if (!name || !municipio || !neighborhood) {
        return Swal.fire("Error", "Por favor, completa todos los campos.", "error");
    }

    const newGroup = {
        name,
        municipality: municipio,
        neighborhood,
        admin: { id: parseInt(adminId) },
    };

    try {
        const response = await fetch(`${URL}/api/group`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newGroup),
        });

        if (response.ok) {
            Swal.fire("Registrado", "El grupo fue registrado exitosamente.", "success");
            // Vaciar los campos del formulario
            document.getElementById("addGroupName").value = "";
            document.getElementById("addGroupMunicipio").value = "";
            document.getElementById("addGroupNeighborhood").value = "";
            await loadTable();
            bootstrap.Modal.getInstance(document.getElementById("addGroupModal")).hide();
        } else {
            Swal.fire("Error", "No se pudo registrar el grupo.", "error");
        }
    } catch (error) {
        Swal.fire("Error", "Ocurrió un problema al registrar el grupo.", "error");
    }
};

// Cargar datos de un grupo en el modal de edición
const loadGroup = async (id) => {
    try {
        const response = await fetch(`${URL}/api/group/${id}`);
        const data = await response.json();
        const group = data.data;

        if (group) {
            document.getElementById("editGroupName").value = group.name;
            document.getElementById("editGroupMunicipio").value = group.municipality;
            document.getElementById("editGroupNeighborhood").value = group.neighborhood;

            // Cargar el administrador actual en el select
            document.getElementById("editAdmin").innerHTML = `<option value="${group.admin?.id || ''}" selected>${group.admin?.name || 'Sin asignar'}</option>`;
        }
    } catch (error) {
        console.error("Error loading group:", error);
    }
};

// Actualizar un grupo
const saveGroupChanges = async () => {
    const id = document.getElementById("editGroupName").dataset.groupId;
    const name = document.getElementById("editGroupName").value.trim();
    const municipio = document.getElementById("editGroupMunicipio").value.trim();
    const neighborhood = document.getElementById("editGroupNeighborhood").value.trim();
    const adminId = document.getElementById("editAdmin").value;

    if (!name || !municipio || !neighborhood) {
        return Swal.fire("Error", "Por favor, completa todos los campos obligatorios.", "error");
    }

    const updatedGroup = {
        id,
        name,
        municipality: municipio,
        neighborhood,
        admin: adminId ? { id: parseInt(adminId) } : null,
    };

    try {
        const response = await fetch(`${URL}/api/group`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedGroup),
        });

        if (response.ok) {
            Swal.fire("Actualizado", "El grupo fue actualizado exitosamente.", "success");
            await loadTable();
            bootstrap.Modal.getInstance(document.getElementById("editGroupModal")).hide();
        } else {
            Swal.fire("Error", "No se pudo actualizar el grupo.", "error");
        }
    } catch (error) {
        Swal.fire("Error", "Ocurrió un problema al actualizar el grupo.", "error");
    }
};

// Eliminar un grupo
const deleteGroup = async (id) => {
    Swal.fire({
        title: "¿Estás seguro?",
        text: "No podrás revertir esta acción.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
    }).then(async (result) => {
        if (result.isConfirmed) {
            try {
                const response = await fetch(`${URL}/api/group/${id}`, {
                    method: "DELETE",
                });

                if (response.ok) {
                    Swal.fire("Eliminado", "El grupo fue eliminado exitosamente.", "success");
                    await loadTable();
                } else {
                    Swal.fire("Error", "No se pudo eliminar el grupo.", "error");
                }
            } catch (error) {
                Swal.fire("Error", "No se pudo eliminar el grupo.", "error");
            }
        }
    });
};

// Ver detalles del grupo
const viewGroup = async (id) => {
    try {
        const response = await fetch(`${URL}/api/group/${id}`);
        const data = await response.json();
        const group = data.data;

        const modalBody = document.querySelector("#viewGroupModal .modal-body");
        if (!modalBody) {
            console.error("El modal para visualizar grupos no está configurado correctamente.");
            return;
        }

        if (group) {
            modalBody.innerHTML = `
                <p><strong>Nombre:</strong> ${group.name}</p>
                <p><strong>Municipio:</strong> ${group.municipality || 'Sin asignar'}</p>
                <p><strong>Colonia:</strong> ${group.neighborhood || 'Sin asignar'}</p>
            `;
        } else {
            modalBody.innerHTML = "<p>No se encontraron detalles para este grupo.</p>";
        }
    } catch (error) {
        console.error("Error viewing group:", error);
        Swal.fire("Error", "No se pudieron cargar los detalles del grupo.", "error");
    }
};

// Cargar administradores en el select
const loadAdmins = async () => {
    try {
        const response = await fetch(`${URL}/api/admins`);
        const data = await response.json();
        const admins = data.data;

        const select = document.getElementById("addAdminGroup");
        select.innerHTML = admins.map(admin => `<option value="${admin.id}">${admin.name}</option>`).join("");
    } catch (error) {
        console.error("Error loading admins:", error);
    }
};

// Inicializar eventos
document.addEventListener("DOMContentLoaded", () => {
    loadTable();
    loadAdmins();
});
