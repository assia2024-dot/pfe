-- Drop tables if they exist
DROP TABLE IF EXISTS audit_categories CASCADE;
DROP TABLE IF EXISTS pieces_jointes CASCADE;
DROP TABLE IF EXISTS liens_externes CASCADE;
DROP TABLE IF EXISTS audits CASCADE;
DROP TABLE IF EXISTS missions CASCADE;
DROP TABLE IF EXISTS magasins CASCADE;
DROP TABLE IF EXISTS regions CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS auditeurs_externes CASCADE;
DROP TABLE IF EXISTS administrateurs CASCADE;
DROP TABLE IF EXISTS auditeurs CASCADE;
DROP TABLE IF EXISTS utilisateurs CASCADE;

-- Users table (parent table for inheritance)
CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    actif BOOLEAN NOT NULL
);

-- Administrators table
CREATE TABLE administrateurs (
    utilisateur_id BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);

-- Auditors table
CREATE TABLE auditeurs (
    utilisateur_id BIGINT NOT NULL PRIMARY KEY,
    telephone VARCHAR(255),
    region VARCHAR(255),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);

-- Regions table
CREATE TABLE regions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL UNIQUE
);

-- Stores table
CREATE TABLE magasins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    ville VARCHAR(255) NOT NULL,
    actif BOOLEAN NOT NULL,
    region_id BIGINT NOT NULL,
    FOREIGN KEY (region_id) REFERENCES regions(id)
);

-- Missions table
CREATE TABLE missions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    statut VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    auditeur_id BIGINT,
    administrateur_id BIGINT,
    magasin_id BIGINT NOT NULL,
    FOREIGN KEY (auditeur_id) REFERENCES auditeurs(utilisateur_id),
    FOREIGN KEY (administrateur_id) REFERENCES administrateurs(utilisateur_id),
    FOREIGN KEY (magasin_id) REFERENCES magasins(id)
);

-- External auditors table
CREATE TABLE auditeurs_externes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    telephone VARCHAR(255),
    statut VARCHAR(255) NOT NULL,
    date_acces TIMESTAMP,
    mission_id BIGINT UNIQUE,
    FOREIGN KEY (mission_id) REFERENCES missions(id)
);

-- Audits table
CREATE TABLE audits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_realisation TIMESTAMP,
    note_globale DOUBLE,
    statut VARCHAR(255) NOT NULL,
    commentaire VARCHAR(255),
    mission_id BIGINT NOT NULL UNIQUE,
    auditeur_id BIGINT,
    magasin_id BIGINT NOT NULL,
    FOREIGN KEY (mission_id) REFERENCES missions(id),
    FOREIGN KEY (auditeur_id) REFERENCES auditeurs(utilisateur_id),
    FOREIGN KEY (magasin_id) REFERENCES magasins(id)
);

-- Categories table
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    note INTEGER
);

-- Audit-Categories junction table
CREATE TABLE audit_categories (
    audit_id BIGINT NOT NULL,
    categorie_id BIGINT NOT NULL,
    PRIMARY KEY (audit_id, categorie_id),
    FOREIGN KEY (audit_id) REFERENCES audits(id),
    FOREIGN KEY (categorie_id) REFERENCES categories(id)
);

-- External links table
CREATE TABLE liens_externes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255) NOT NULL UNIQUE,
    code_acces VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    utilise BOOLEAN NOT NULL,
    telephone VARCHAR(255),
    administrateur_id BIGINT NOT NULL,
    magasin_id BIGINT NOT NULL,
    auditeur_externe_id BIGINT UNIQUE,
    FOREIGN KEY (administrateur_id) REFERENCES administrateurs(utilisateur_id),
    FOREIGN KEY (magasin_id) REFERENCES magasins(id),
    FOREIGN KEY (auditeur_externe_id) REFERENCES auditeurs_externes(id)
);

-- Attachments table
CREATE TABLE pieces_jointes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    taille BIGINT,
    uploaded_at TIMESTAMP,
    categorie_id BIGINT NOT NULL,
    FOREIGN KEY (categorie_id) REFERENCES categories(id)
);