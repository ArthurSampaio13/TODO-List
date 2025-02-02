const $ = (selector) => document.querySelector(selector);
const showElement = (element) => element.classList.remove('hidden');
const hideElement = (element) => element.classList.add('hidden');

function showToast(message, type = 'success') {
    const toast = $('#toast');
    toast.textContent = message;
    toast.className = `toast ${type} animate__animated animate__fadeIn`;
    showElement(toast);
    setTimeout(() => {
        toast.classList.add('animate__fadeOut');
        setTimeout(() => hideElement(toast), 500);
    }, 3000);
}

function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function showRegister() {
    $('#login-container').classList.add('animate__fadeOut');
    setTimeout(() => {
        hideElement($('#login-container'));
        showElement($('#register-container'));
        $('#register-container').classList.remove('animate__fadeOut');
        $('#register-container').classList.add('animate__fadeIn');
    }, 500);
}

function showLogin() {
    $('#register-container').classList.add('animate__fadeOut');
    setTimeout(() => {
        hideElement($('#register-container'));
        showElement($('#login-container'));
        $('#login-container').classList.remove('animate__fadeOut');
        $('#login-container').classList.add('animate__fadeIn');
    }, 500);
}

function handleLogin(event) {
    event.preventDefault();
    const email = $('#login-email').value;
    const password = $('#login-password').value;

    if (!validateEmail(email)) {
        showToast('Email inválido', 'error');
        return;
    }

    const users = JSON.parse(localStorage.getItem('users')) || [];
    const user = users.find(u => u.email === email && u.password === password);

    if (user) {
        localStorage.setItem('currentUser', JSON.stringify(user));
        showToast('Login realizado com sucesso!');
        hideElement($('#login-container'));
        showElement($('#task-container'));
        loadTasks();
    } else {
        showToast('Email ou senha incorretos', 'error');
    }
}

function handleRegister(event) {
    event.preventDefault();
    const name = $('#register-name').value;
    const email = $('#register-email').value;
    const password = $('#register-password').value;

    if (!name.trim()) {
        showToast('Nome é obrigatório', 'error');
        return;
    }

    if (!validateEmail(email)) {
        showToast('Email inválido', 'error');
        return;
    }

    const users = JSON.parse(localStorage.getItem('users')) || [];

    if (users.some(u => u.email === email)) {
        showToast('Email já cadastrado', 'error');
        return;
    }

    users.push({ name, email, password });
    localStorage.setItem('users', JSON.stringify(users));
    showToast('Cadastro realizado com sucesso!');
    showLogin();
}

function logout() {
    localStorage.removeItem('currentUser');
    hideElement($('#task-container'));
    showElement($('#login-container'));
    showToast('Logout realizado com sucesso!');
}

function showTaskForm(taskToEdit = null) {
    const formTitle = $('#task-form-title');
    const form = $('#task-form');

    if (taskToEdit) {
        formTitle.textContent = 'Editar Tarefa';
        $('#task-title').value = taskToEdit.title;
        $('#task-desc').value = taskToEdit.desc;
        $('#task-priority').value = taskToEdit.priority;
        $('#task-status').value = taskToEdit.status;
        $('#task-date').value = taskToEdit.date;
        $('#task-category').value = taskToEdit.category;
        form.dataset.editIndex = taskToEdit.index;
    } else {
        formTitle.textContent = 'Nova Tarefa';
        form.reset();
        delete form.dataset.editIndex;
    }

    showElement($('#task-form-container'));
}

function hideTaskForm() {
    hideElement($('#task-form-container'));
}

function handleTaskSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const task = {
        title: $('#task-title').value,
        desc: $('#task-desc').value,
        priority: $('#task-priority').value,
        status: $('#task-status').value,
        date: $('#task-date').value,
        category: $('#task-category').value
    };

    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];

    if (form.dataset.editIndex) {
        tasks[form.dataset.editIndex] = task;
        showToast('Tarefa atualizada com sucesso!');
    } else {
        tasks.push(task);
        showToast('Tarefa criada com sucesso!');
    }

    localStorage.setItem('tasks', JSON.stringify(tasks));
    hideTaskForm();
    loadTasks();
}

function deleteTask(index) {
    if (confirm('Tem certeza que deseja excluir esta tarefa?')) {
        let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
        tasks.splice(index, 1);
        localStorage.setItem('tasks', JSON.stringify(tasks));
        showToast('Tarefa excluída com sucesso!');
        loadTasks();
    }
}

function editTask(index) {
    const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    const task = { ...tasks[index], index };
    showTaskForm(task);
}

function loadTasks() {
    const taskList = $('#task-list');
    const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    const filterStatus = $('#filter-status').value;

    let filteredTasks = tasks;
    if (filterStatus !== 'all') {
        filteredTasks = tasks.filter(task => task.status === filterStatus);
    }

    taskList.innerHTML = filteredTasks.map((task, index) => `
        <div class="task-item animate__animated animate__fadeIn">
            <h3 class="task-title">${task.title}</h3>
            <p class="task-description">${task.desc}</p>
            <div class="task-meta">
                <span>Prioridade: ${task.priority}</span>
                <span>Status: ${task.status}</span>
                <span>Categoria: ${task.category}</span>
                <span>Data: ${new Date(task.date).toLocaleDateString()}</span>
            </div>
            <div class="task-actions">
                <button onclick="editTask(${index})" class="btn btn-outline">Editar</button>
                <button onclick="deleteTask(${index})" class="btn btn-danger">Excluir</button>
            </div>
        </div>
    `).join('');
}

function filterTasks() {
    loadTasks();
}

function sortTasks() {
    const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    const sortBy = $('#sort-options').value;

    tasks.sort((a, b) => {
        switch (sortBy) {
            case 'priority':
                return b.priority - a.priority;
            case 'date':
                return new Date(a.date) - new Date(b.date);
            case 'status':
                return a.status.localeCompare(b.status);
            case 'category':
                return a.category.localeCompare(b.category);
            default:
                return 0;
        }
    });

    localStorage.setItem('tasks', JSON.stringify(tasks));
    loadTasks();
}

document.addEventListener('DOMContentLoaded', () => {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
        hideElement($('#login-container'));
        showElement($('#task-container'));
        loadTasks();
    }
});