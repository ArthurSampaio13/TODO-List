document.addEventListener("DOMContentLoaded", () => {
    loadTasks();
});

function showRegister() {
    document.getElementById('login-container').classList.add('hidden');
    document.getElementById('register-container').classList.remove('hidden');
}

function showLogin() {
    document.getElementById('register-container').classList.add('hidden');
    document.getElementById('login-container').classList.remove('hidden');
}

function login() {
    document.getElementById('login-container').classList.add('hidden');
    document.getElementById('task-container').classList.remove('hidden');
}

function register() {
    alert('Cadastro realizado! Agora faça login.');
    showLogin();
}

function logout() {
    document.getElementById('task-container').classList.add('hidden');
    document.getElementById('login-container').classList.remove('hidden');
}

function showTaskForm() {
    document.getElementById('task-form-container').classList.remove('hidden');
}

function hideTaskForm() {
    document.getElementById('task-form-container').classList.add('hidden');
}

function saveTask() {
    let title = document.getElementById('task-title').value;
    let desc = document.getElementById('task-desc').value;
    let priority = document.getElementById('task-priority').value;
    let status = document.getElementById('task-status').value;
    let date = document.getElementById('task-date').value;
    let category = document.getElementById('task-category').value;

    let task = { title, desc, priority, status, date, category };
    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    tasks.push(task);
    localStorage.setItem('tasks', JSON.stringify(tasks));

    loadTasks();
    hideTaskForm();
}

function loadTasks() {
    let taskList = document.getElementById('task-list');
    taskList.innerHTML = '';

    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    tasks.forEach((task, index) => {
        let taskItem = document.createElement('li');
        taskItem.classList.add('task-item');
        taskItem.innerHTML = `
            <strong>${task.title}</strong>
            <p>${task.desc}</p>
            <small>Prioridade: ${task.priority} | Status: ${task.status} | Categoria: ${task.category} | Data: ${task.date}</small>
            <div class="task-actions">
                <button onclick="editTask(${index})">Editar</button>
                <button onclick="deleteTask(${index})">Excluir</button>
            </div>
        `;
        taskList.appendChild(taskItem);
    });
}

function deleteTask(index) {
    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    tasks.splice(index, 1);
    localStorage.setItem('tasks', JSON.stringify(tasks));
    loadTasks();
}

function editTask(index) {
    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    let task = tasks[index];

    document.getElementById('task-title').value = task.title;
    document.getElementById('task-desc').value = task.desc;
    document.getElementById('task-priority').value = task.priority;
    document.getElementById('task-status').value = task.status;
    document.getElementById('task-date').value = task.date;
    document.getElementById('task-category').value = task.category;

    showTaskForm();

    deleteTask(index);
}
