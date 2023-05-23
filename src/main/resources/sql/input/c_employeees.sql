CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100),
    department_id INT,
    hire_date DATE,
    salary DECIMAL(10,2),
    CONSTRAINT fk_department
        FOREIGN KEY (department_id) REFERENCES departments(department_id)
        ON DELETE CASCADE
);