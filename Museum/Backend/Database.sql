-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema museum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema museum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `museum` DEFAULT CHARACTER SET utf8 ;
USE `museum` ;



-- -----------------------------------------------------
-- Table `museum`.`MuseumTypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`MuseumTypes` (
  `museumTypeID` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`museumTypeID`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `museum`.`Museums`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`Museums` (
  `museumID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `latitude` DECIMAL(10,8) NOT NULL,
  `longitude` DECIMAL(10,8) NOT NULL,
  `thumbnail` VARCHAR(255) NULL,
  `type` INT NOT NULL,
  `countryAlpha2Code` CHAR(2) NOT NULL,
  `createdAt` DATETIME NOT NULL,
  `updatedAt` DATETIME NULL,
  `createdBy` INT NOT NULL,
  `updatedBy` INT NULL,
  INDEX `museums_createdBy_idx` (`createdBy` ASC) VISIBLE,
  INDEX `museums_updatedBy_idx` (`updatedBy` ASC) VISIBLE,
  CONSTRAINT `museums_createdBy_FK`
    FOREIGN KEY (`createdBy`)
    REFERENCES `museum`.`users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `museums_updatedBy_FK`
    FOREIGN KEY (`updatedBy`)
    REFERENCES `museum`.`users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  PRIMARY KEY (`museumID`),
  INDEX `type_idx` (`type` ASC) VISIBLE,
  CONSTRAINT `museums_type_FK`
    FOREIGN KEY (`type`)
    REFERENCES `museum`.`MuseumTypes` (`museumTypeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `museum`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`Users` (
  `userID` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `active` TINYINT NOT NULL,
  `createdAt` DATETIME NOT NULL,
  `updatedAt` DATETIME NULL,
  `updatedBy` INT NULL,
  INDEX `users_updatedBy_idx` (`updatedBy` ASC) VISIBLE,
  CONSTRAINT `users_updatedBy_FK`
    FOREIGN KEY (`updatedBy`)
    REFERENCES `museum`.`users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  PRIMARY KEY (`userID`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `museum`.`Tours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`Tours` (
  `tourID` INT NOT NULL AUTO_INCREMENT,
  `museum` INT NOT NULL,
  `startTimestamp` DATETIME NOT NULL,
  `endTimeStamp` DATETIME NOT NULL,
  `price` DECIMAL(10,4) NOT NULL,
  `createdAt` DATETIME NOT NULL,
  `updatedAt` DATETIME NULL,
  `createdBy` INT NOT NULL,
  `updatedBy` INT NULL,
  INDEX `tours_createdBy_idx` (`createdBy` ASC) VISIBLE,
  INDEX `tours_updatedBy_idx` (`updatedBy` ASC) VISIBLE,
  CONSTRAINT `tours_createdBy_FK`
    FOREIGN KEY (`createdBy`)
    REFERENCES `museum`.`users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `tours_updatedBy_FK`
    FOREIGN KEY (`updatedBy`)
    REFERENCES `museum`.`users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  PRIMARY KEY (`tourID`),
  INDEX `museum_idx` (`museum` ASC) VISIBLE,
  CONSTRAINT `museum`
    FOREIGN KEY (`museum`)
    REFERENCES `museum`.`Museums` (`museumID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `museum`.`TourPurchases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`TourPurchases` (
  `purchaseID` BINARY(16) NOT NULL,
  `tour` INT NOT NULL,
  `user` INT NOT NULL,
  `purchased` DATETIME NOT NULL,
  `paid` DATETIME NULL,
  `paymentURL` VARCHAR(255) NULL,
  PRIMARY KEY (`purchaseID`),
  INDEX `tour_idx` (`tour` ASC) VISIBLE,
  INDEX `user_idx` (`user` ASC) VISIBLE,
  CONSTRAINT `tour`
    FOREIGN KEY (`tour`)
    REFERENCES `museum`.`Tours` (`tourID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user`
    FOREIGN KEY (`user`)
    REFERENCES `museum`.`Users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `museum`.`TourStaticContent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`TourStaticContent` (
  `tourStaticContentID` INT NOT NULL AUTO_INCREMENT,
  `URI` VARCHAR(255) NOT NULL,
  `tour` INT NOT NULL,
  `isYouTubeVideo` TINYINT NULL,
  `type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`tourStaticContentID`),
  INDEX `tour_idx` (`tour` ASC) VISIBLE,
  CONSTRAINT `tour_static_content_tour_FK`
    FOREIGN KEY (`tour`)
    REFERENCES `museum`.`Tours` (`tourID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `museum`.`AccessTokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`AccessTokens` (
  `token` BINARY(16) NOT NULL,
  `user` INT NOT NULL,
  `created` DATETIME NOT NULL,
  `validUntil` DATETIME NOT NULL,
  `valid` TINYINT NOT NULL,
  PRIMARY KEY (`token`),
  INDEX `user_idx` (`user` ASC) VISIBLE,
  CONSTRAINT `access_tokens_user_FK`
    FOREIGN KEY (`user`)
    REFERENCES `museum`.`Users` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `museum`.`EventNotifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `museum`.`EventNotifications` (
  `id` INT AUTO_INCREMENT,
  `sent` TINYINT NOT NULL,
  `tourID` INT NOT NULL,
  `sendDateTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `notifications_event_FK`
	FOREIGN KEY (`tourID`)
	REFERENCES `museum`.`Tours`(`tourID`)
	ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
