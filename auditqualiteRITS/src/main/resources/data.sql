-- ========================
-- 1. Insert Users
-- ========================

-- Administrators (role = ADMIN)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, actif) VALUES
('Admin', 'Principal', 'admin@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true),
('Martin', 'Sophie', 'sophie.martin@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true),
('Bernard', 'Thomas', 'thomas.bernard@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true);

INSERT INTO administrateurs (utilisateur_id) VALUES (1), (2), (3);

-- Auditors (role = AUDITEUR)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, actif) VALUES
('Dubois', 'Jean', 'jean.dubois@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Petit', 'Marie', 'marie.petit@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Robert', 'Pierre', 'pierre.robert@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Richard', 'Isabelle', 'isabelle.richard@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Durand', 'Nicolas', 'nicolas.durand@audit.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true);

INSERT INTO auditeurs (utilisateur_id, telephone, region) VALUES
(4, '0612345678', 'Île-de-France'),
(5, '0623456789', 'Auvergne-Rhône-Alpes'),
(6, '0634567890', 'Nouvelle-Aquitaine'),
(7, '0645678901', 'Occitanie'),
(8, '0656789012', 'Hauts-de-France');

-- ========================
-- 2. Insert Regions
-- ========================

INSERT INTO regions (nom, code) VALUES
('Île-de-France', 'IDF'),
('Auvergne-Rhône-Alpes', 'ARA'),
('Nouvelle-Aquitaine', 'NAQ'),
('Occitanie', 'OCC'),
('Hauts-de-France', 'HDF'),
('Provence-Alpes-Côte d''Azur', 'PAC'),
('Grand Est', 'GES'),
('Pays de la Loire', 'PDL');

-- ========================
-- 3. Insert Stores
-- ========================

INSERT INTO magasins (nom, adresse, ville, actif, region_id) VALUES
('Magasin Paris Centre', '10 Rue de Rivoli', 'Paris', true, 1),
('Magasin Lyon Part-Dieu', '20 Boulevard Vivier Merle', 'Lyon', true, 2),
('Magasin Bordeaux Centre', '30 Rue Sainte-Catherine', 'Bordeaux', true, 3),
('Magasin Toulouse Capitole', '40 Rue Alsace Lorraine', 'Toulouse', true, 4),
('Magasin Lille Flandres', '50 Rue Nationale', 'Lille', true, 5),
('Magasin Marseille Vieux-Port', '60 Rue Saint-Ferréol', 'Marseille', true, 6),
('Magasin Strasbourg Centre', '70 Rue des Grandes Arcades', 'Strasbourg', true, 7),
('Magasin Nantes Commerce', '80 Rue de Verdun', 'Nantes', true, 8);

-- ========================
-- 4. Insert Missions
-- ========================

INSERT INTO missions (titre, date_debut, date_fin, statut, type, auditeur_id, administrateur_id, magasin_id) VALUES
('Audit Qualité Paris', '2026-01-10', '2026-01-20', 'COMPLETED', 'INTERNE', 4, 1, 1),
('Audit Sécurité Lyon', '2026-02-05', '2026-02-15', 'EN_COURS', 'INTERNE', 5, 2, 2),
('Audit Conformité Bordeaux', '2026-03-01', '2026-03-10', 'PLANIFIEE', 'EXTERNE', 6, 1, 3),
('Audit Performance Toulouse', '2026-04-10', '2026-04-20', 'PLANIFIEE', 'INTERNE', 7, 3, 4),
('Audit Environnemental Lille', '2026-05-01', '2026-05-15', 'PLANIFIEE', 'EXTERNE', 8, 2, 5),
('Audit Financier Marseille', '2026-03-15', '2026-03-25', 'EN_COURS', 'INTERNE', 4, 1, 6),
('Audit Social Strasbourg', '2026-04-05', '2026-04-18', 'PLANIFIEE', 'EXTERNE', 5, 3, 7),
('Audit Technique Nantes', '2026-02-20', '2026-02-28', 'COMPLETED', 'INTERNE', 6, 2, 8);

-- ========================
-- 5. Insert External Auditors
-- ========================

INSERT INTO auditeurs_externes (nom, telephone, statut, date_acces, mission_id) VALUES
('Cabinet Deloitte', '0145678901', 'ACTIF', '2026-03-01 09:00:00', 3),
('Cabinet EY', '0156789012', 'ACTIF', '2026-05-01 10:00:00', 5),
('Cabinet KPMG', '0167890123', 'ACTIF', '2026-04-05 11:00:00', 7),
('Cabinet PwC', '0178901234', 'INACTIF', NULL, NULL),
('Cabinet Mazars', '0189012345', 'ACTIF', NULL, NULL);

-- ========================
-- 6. Insert Audits
-- ========================

INSERT INTO audits (date_realisation, note_globale, statut, commentaire, mission_id, auditeur_id, magasin_id) VALUES
('2026-01-20 14:30:00', 85.5, 'COMPLETED', 'Audit réussi, quelques points d''amélioration', 1, 4, 1),
('2026-02-15 10:00:00', NULL, 'EN_COURS', 'Audit en cours d''analyse', 2, 5, 2),
('2026-03-28 16:00:00', 92.0, 'COMPLETED', 'Excellente performance', 6, 4, 6),
('2026-02-28 11:30:00', 78.5, 'COMPLETED', 'Besoin d''amélioration sur plusieurs points', 8, 6, 8);

-- ========================
-- 7. Insert Categories
-- ========================

INSERT INTO categories (nom, description, note) VALUES
('Qualité Service', 'Évaluation de la qualité du service client', 85),
('Sécurité', 'Conformité aux normes de sécurité', 90),
('Hygiène', 'Propreté et hygiène des locaux', 88),
('Documentation', 'Gestion des documents administratifs', 75),
('Formation', 'Formation du personnel', 82),
('Matériel', 'État et maintenance des équipements', 80),
('Informatique', 'Systèmes informatiques et logiciels', 78),
('Stock', 'Gestion des stocks et inventaires', 86);

-- ========================
-- 8. Link Audits with Categories
-- ========================

INSERT INTO audit_categories (audit_id, categorie_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5), (2, 6),
(3, 1), (3, 7), (3, 8),
(4, 2), (4, 3), (4, 4);

-- ========================
-- 9. Insert External Links
-- ========================

INSERT INTO liens_externes (url, code_acces, expires_at, utilise, telephone, administrateur_id, magasin_id, auditeur_externe_id) VALUES
('https://audit.qualite.com/paris2026', 'ACCES001', '2026-12-31 23:59:59', false, '0612345678', 1, 1, NULL),
('https://audit.qualite.com/lyon2026', 'ACCES002', '2026-12-31 23:59:59', false, '0623456789', 2, 2, NULL),
('https://audit.qualite.com/bordeaux2026', 'ACCES003', '2026-12-31 23:59:59', false, '0634567890', 1, 3, 1),
('https://audit.qualite.com/toulouse2026', 'ACCES004', '2026-12-31 23:59:59', true, '0645678901', 3, 4, NULL),
('https://audit.qualite.com/lille2026', 'ACCES005', '2026-12-31 23:59:59', false, '0656789012', 2, 5, 2);

-- ========================
-- 10. Insert Attachments
-- ========================

INSERT INTO pieces_jointes (url, type, taille, uploaded_at, categorie_id) VALUES
('/uploads/rapport_paris.pdf', 'PDF', 1048576, '2026-01-20 15:00:00', 1),
('/uploads/checklist_lyon.pdf', 'PDF', 524288, '2026-02-15 11:00:00', 4),
('/uploads/photo_audit.jpg', 'JPG', 2097152, '2026-03-28 16:30:00', 2),
('/uploads/note_formulaire.xlsx', 'XLSX', 262144, '2026-02-28 12:00:00', 3),
('/uploads/audit_mars_2026.pdf', 'PDF', 3145728, '2026-03-20 10:00:00', 1),
('/uploads/recommandations.docx', 'DOCX', 1572864, '2026-04-10 14:00:00', 5);

-- ========================
-- Display statistics
-- ========================
SELECT '=== Database Initialized ===' AS Status;
SELECT COUNT(*) AS Total_Users FROM utilisateurs;
SELECT COUNT(*) AS Total_Stores FROM magasins;
SELECT COUNT(*) AS Total_Missions FROM missions;
SELECT COUNT(*) AS Total_Audits FROM audits;
SELECT COUNT(*) AS Total_Categories FROM categories;