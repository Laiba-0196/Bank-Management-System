create database bank2;
use bank2;
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL
);

CREATE TABLE Accounts (
    AccountNumber VARCHAR(20) PRIMARY KEY,
    Balance DECIMAL(10, 2) NOT NULL,
    UserID INT NOT NULL,
    AccountType ENUM('SAVINGS', 'CURRENT'),
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);

CREATE TABLE Transactions (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    AccountNumber VARCHAR(20),
    Amount DECIMAL(10, 2) NOT NULL,
    TransactionType ENUM('DEPOSIT', 'WITHDRAWAL'),
    Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AccountNumber) REFERENCES Accounts(AccountNumber) ON DELETE CASCADE
);
Select * from users;
Select * From accounts;
select * from transactions;








