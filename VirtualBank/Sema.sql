-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema VirtualBank
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema VirtualBank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `VirtualBank` DEFAULT CHARACTER SET utf8 ;
USE `VirtualBank` ;

-- -----------------------------------------------------
-- Table `VirtualBank`.`Clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VirtualBank`.`Clients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `availableFunds` DECIMAL(12,4) NOT NULL DEFAULT 0,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VirtualBank`.`Companies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VirtualBank`.`Companies` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `token_UNIQUE` (`token` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `companies_id_FK`
    FOREIGN KEY (`id`)
    REFERENCES `VirtualBank`.`Clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VirtualBank`.`Persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VirtualBank`.`Persons` (
  `id` INT NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `cardNumber` CHAR(16) NOT NULL,
  `cardType` VARCHAR(45) NOT NULL,
  `cardExpirationDate` DATE NOT NULL,
  `pin` CHAR(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cardNumber_UNIQUE` (`cardNumber` ASC) VISIBLE,
  CONSTRAINT `persons_id_FK`
    FOREIGN KEY (`id`)
    REFERENCES `VirtualBank`.`Clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VirtualBank`.`Transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VirtualBank`.`Transactions` (
  `id` BINARY(16) NOT NULL,
  `receiver` INT NOT NULL,
  `amount` DECIMAL(12,4) NOT NULL,
  `timestamp` DATETIME NOT NULL,
  `sender` INT NULL,
  `notificationURL` VARCHAR(255) NULL,
  `scratchString` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `receiver_idx` (`receiver` ASC) VISIBLE,
  INDEX `sender_idx` (`sender` ASC) VISIBLE,
  CONSTRAINT `receiver`
    FOREIGN KEY (`receiver`)
    REFERENCES `VirtualBank`.`Companies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sender`
    FOREIGN KEY (`sender`)
    REFERENCES `VirtualBank`.`Persons` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
