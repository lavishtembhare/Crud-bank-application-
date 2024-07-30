Install mysql workbench
connection name:- localhost
user:- root
password:- 
Create database:
CREATE DATABASE bankdb;

Use Database:
USE bankdb;

Crate Table: 
CREATE TABLE bankaccount (
    acc_no VARCHAR(20) PRIMARY KEY,
    holder_Name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    balance DOUBLE NOT NULL,
    category ENUM('SAVINGS', 'CURRENT', 'FIXED_DEPOSIT', 'LOAN') NOT NULL
);
