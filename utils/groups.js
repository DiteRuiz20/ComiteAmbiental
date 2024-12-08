const URL = 'http://localhost:8080';
let groupList = [];
let adminList = []; // Variable para almacenar administradores

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
        console.error('Error al obtener grupos:', error);
    }
};

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

// Cargar la tabla de grupos con administradores asignados
const loadTable = async () => {
    await findAllGroups();
    await findAllAdmins(); // Asegurar que se tienen los administradores disponibles
    const tbody = document.querySelector("tbody");
    tbody.innerHTML = groupList.length
        ? groupList.map((group, index) => `
            <tr>
                <th>${index + 1}</th>
                <td>${group.name}</td>
                <td>${group.admin ? `${group.admin.name} ${group.admin.lastname}` : 'Aún no cuenta con un administrador'}</td>
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

    if (!name || !municipio || !neighborhood || !adminId) {
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
        await findAllAdmins(); // Asegurarse de tener los administradores antes de cargar
        const response = await fetch(`${URL}/api/group/${id}`);
        const data = await response.json();
        const group = data.data;

        if (group) {
            document.getElementById("editGroupName").value = group.name;
            document.getElementById("editGroupMunicipio").value = group.municipality;
            document.getElementById("editGroupNeighborhood").value = group.neighborhood;

            // Cargar administradores dinámicamente en el select y seleccionar el actual
            const adminSelect = document.getElementById("editAdmin");
            adminSelect.innerHTML = adminList.map(admin => `
                <option value="${admin.id}" ${group.admin?.id === admin.id ? "selected" : ""}>
                    ${admin.name} ${admin.lastname}
                </option>
            `).join("");
        }
    } catch (error) {
        console.error("Error al cargar el grupo:", error);
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

// Cargar administradores en el modal de "Agregar Grupo"
const loadAdminOptions = async () => {
    await findAllAdmins();
    const adminSelect = document.getElementById("addAdminGroup");
    adminSelect.innerHTML = adminList.map(admin => `
        <option value="${admin.id}">${admin.name} ${admin.lastname}</option>
    `).join("");
};

// Limpiar campos del modal "Agregar Grupo" al cerrarlo
document.getElementById("addGroupModal").addEventListener("hidden.bs.modal", () => {
    document.getElementById("addGroupName").value = "";
    document.getElementById("addGroupMunicipio").value = "";
    document.getElementById("addGroupNeighborhood").value = "";
    document.getElementById("addAdminGroup").innerHTML = ""; // Limpia el select
});

// Limpiar campos del modal "Editar Grupo" al cerrarlo
document.getElementById("editGroupModal").addEventListener("hidden.bs.modal", () => {
    document.getElementById("editGroupName").value = "";
    document.getElementById("editGroupMunicipio").value = "";
    document.getElementById("editGroupNeighborhood").value = "";
    document.getElementById("editAdmin").innerHTML = ""; // Limpia el select
});

// Inicializar eventos
document.addEventListener("DOMContentLoaded", () => {
    loadTable();
    loadAdminOptions(); // Cargar los administradores al inicializar
});
