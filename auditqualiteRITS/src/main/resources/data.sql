-- ========================
-- 1. Insert Users (Moroccan names)
-- ========================

-- Administrators (role = ADMIN)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, actif) VALUES
('Benali', 'Mohammed', 'mohammed.benali@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true),
('El Fassi', 'Fatima', 'fatima.elfassi@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true),
('Alaoui', 'Youssef', 'youssef.alaoui@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true),
('Tazi', 'Nadia', 'nadia.tazi@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN', true);

INSERT INTO administrateurs (utilisateur_id) VALUES (1), (2), (3), (4);

-- Auditors (role = AUDITEUR)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, actif) VALUES
('Amrani', 'Karim', 'karim.amrani@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Benjelloun', 'Leila', 'leila.benjelloun@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Cherkaoui', 'Omar', 'omar.cherkaoui@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Benchekroun', 'Sofia', 'sofia.benchekroun@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('El Mansouri', 'Hicham', 'hicham.elmansouri@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Belkacem', 'Rachida', 'rachida.belkacem@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Zniber', 'Mehdi', 'mehdi.zniber@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
('Kabbaj', 'Nour', 'nour.kabbaj@audit.ma', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true);

INSERT INTO auditeurs (utilisateur_id, telephone, region) VALUES
(5, '0612345678', 'Casablanca-Settat'),
(6, '0623456789', 'Rabat-Salé-Kénitra'),
(7, '0634567890', 'Marrakech-Safi'),
(8, '0645678901', 'Fès-Meknès'),
(9, '0656789012', 'Tanger-Tétouan-Al Hoceïma'),
(10, '0667890123', 'Casablanca-Settat'),
(11, '0678901234', 'Rabat-Salé-Kénitra'),
(12, '0689012345', 'Marrakech-Safi');

-- ========================
-- 2. Insert Moroccan Regions
-- ========================

INSERT INTO regions (nom, code) VALUES
('Casablanca-Settat', 'CAS'),
('Rabat-Salé-Kénitra', 'RSK'),
('Marrakech-Safi', 'MRS'),
('Fès-Meknès', 'FEZ'),
('Tanger-Tétouan-Al Hoceïma', 'TNG'),
('Souss-Massa', 'SMA'),
('Oriental', 'ORI'),
('Béni Mellal-Khénifra', 'BMK'),
('Laâyoune-Sakia El Hamra', 'LSH'),
('Dakhla-Oued Ed-Dahab', 'DED');

-- ========================
-- 3. Insert Moroccan Stores (Magasins)
-- ========================

-- Casablanca stores
INSERT INTO magasins (nom, adresse, ville, actif, region_id) VALUES
('Magasin Casa Morocco Mall', 'Boulevard Sidi Mohamed Ben Abdellah', 'Casablanca', true, 1),
('Magasin Casa Anfa', 'Boulevard d''Anfa', 'Casablanca', true, 1),
('Magasin Casa Maarif', 'Rue Tiznit, Quartier Maarif', 'Casablanca', true, 1),
('Magasin Casa Derb Ghallef', 'Boulevard Moulay Youssef', 'Casablanca', true, 1),

-- Rabat stores
('Magasin Rabat Agdal', 'Avenue Mohamed VI, Agdal', 'Rabat', true, 2),
('Magasin Rabat Hay Riad', 'Centre Commercial Maga Mall', 'Rabat', true, 2),
('Magasin Rabat Souissi', 'Route des Zaërs', 'Rabat', true, 2),

-- Marrakech stores
('Magasin Marrakech Gueliz', 'Avenue Mohammed V, Gueliz', 'Marrakech', true, 3),
('Magasin Marrakech Menara', 'Boulevard de la Ménara', 'Marrakech', true, 3),
('Magasin Marrakech Palmeraie', 'Route de Fès', 'Marrakech', true, 3),

-- Fes stores
('Magasin Fes Atlas', 'Boulevard Mohammed V', 'Fès', true, 4),
('Magasin Fes Saiss', 'Route de Meknès', 'Fès', true, 4),

-- Tangier stores
('Magasin Tanger Centre', 'Boulevard Pasteur', 'Tanger', true, 5),
('Magasin Tanger Malabata', 'Route de Malabata', 'Tanger', true, 5),
('Magasin Tanger Ibn Batouta', 'Avenue Mohamed VI', 'Tanger', true, 5),

-- Agadir stores
('Magasin Agadir Centre', 'Boulevard Hassan II', 'Agadir', true, 6),
('Magasin Agadir Marina', 'Boulevard du 20 Août', 'Agadir', true, 6),

-- Oujda stores
('Magasin Oujda Centre', 'Boulevard Abdelkrim El Khattabi', 'Oujda', true, 7);

-- ========================
-- 4. Insert Missions (Moroccan context)
-- ========================

INSERT INTO missions (titre, date_debut, date_fin, statut, type, auditeur_id, administrateur_id, magasin_id) VALUES
('Audit Qualité Casablanca Morocco Mall', '2026-01-10', '2026-01-20', 'COMPLETED', 'INTERNE', 5, 1, 1),
('Audit Sécurité Rabat Agdal', '2026-02-05', '2026-02-15', 'EN_COURS', 'INTERNE', 6, 2, 5),
('Audit Conformité Marrakech Gueliz', '2026-03-01', '2026-03-10', 'PLANIFIEE', 'EXTERNE', 7, 1, 8),
('Audit Performance Tanger Centre', '2026-04-10', '2026-04-20', 'PLANIFIEE', 'INTERNE', 9, 3, 13),
('Audit Environnemental Fes Atlas', '2026-05-01', '2026-05-15', 'PLANIFIEE', 'EXTERNE', 8, 2, 11),
('Audit Financier Casa Anfa', '2026-03-15', '2026-03-25', 'EN_COURS', 'INTERNE', 5, 1, 2),
('Audit Social Agadir Centre', '2026-04-05', '2026-04-18', 'PLANIFIEE', 'EXTERNE', 10, 4, 16),
('Audit Technique Rabat Hay Riad', '2026-02-20', '2026-02-28', 'COMPLETED', 'INTERNE', 6, 2, 6),
('Audit Qualité Tanger Malabata', '2026-06-01', '2026-06-15', 'PLANIFIEE', 'INTERNE', 11, 3, 14),
('Audit Sécurité Oujda Centre', '2026-07-10', '2026-07-20', 'PLANIFIEE', 'INTERNE', 12, 1, 18),
('Audit Conformité Casa Maarif', '2026-01-25', '2026-02-05', 'COMPLETED', 'INTERNE', 5, 2, 3),
('Audit Performance Marrakech Menara', '2026-03-20', '2026-03-30', 'EN_COURS', 'EXTERNE', 7, 3, 9);

-- ========================
-- 5. Insert External Auditors (Cabinets marocains)
-- ========================

INSERT INTO auditeurs_externes (nom, telephone, statut, date_acces, mission_id) VALUES
('Cabinet KPMG Maroc', '0522123456', 'ACTIF', '2026-03-01 09:00:00', 3),
('Cabinet PwC Maroc', '0522789012', 'ACTIF', '2026-05-01 10:00:00', 5),
('Cabinet EY Maroc', '0522345678', 'ACTIF', '2026-04-05 11:00:00', 7),
('Cabinet Deloitte Maroc', '0522567890', 'ACTIF', '2026-03-20 09:00:00', 12),
('Cabinet Mazars Maroc', '0522901234', 'INACTIF', NULL, NULL),
('Cabinet Grant Thornton Maroc', '0522678901', 'ACTIF', NULL, NULL);

-- ========================
-- 6. Insert Audits
-- ========================

INSERT INTO audits (date_realisation, note_globale, statut, commentaire, mission_id, auditeur_id, magasin_id) VALUES
('2026-01-20 14:30:00', 85.5, 'COMPLETED', 'Audit réussi, quelques points d''amélioration sur la gestion des stocks', 1, 5, 1),
('2026-02-15 10:00:00', NULL, 'EN_COURS', 'Audit en cours, résultats préliminaires satisfaisants', 2, 6, 5),
('2026-03-28 16:00:00', 92.0, 'COMPLETED', 'Excellente performance financière', 6, 5, 2),
('2026-02-28 11:30:00', 78.5, 'COMPLETED', 'Besoin d''amélioration sur la conformité réglementaire', 8, 6, 6),
('2026-02-05 15:00:00', 88.0, 'COMPLETED', 'Très bonne gestion de la qualité', 11, 5, 3),
('2026-03-30 14:00:00', NULL, 'EN_COURS', 'Audit en cours d''analyse', 12, 7, 9);

-- ========================
-- 7. Insert Categories (Moroccan standards)
-- ========================

INSERT INTO categories (nom, description, note) VALUES
('Qualité Service Client', 'Évaluation de la qualité du service client selon normes marocaines', 85),
('Conformité Légale', 'Respect des lois et règlements marocains', 90),
('Hygiène et Sécurité', 'Normes d''hygiène et sécurité dans les locaux', 88),
('Documentation Administrative', 'Gestion des documents et archives', 75),
('Formation Personnel', 'Formation continue des employés', 82),
('Gestion des Stocks', 'Efficacité de la gestion des stocks', 80),
('Infrastructure IT', 'Systèmes informatiques et logiciels', 78),
('Gestion Financière', 'Comptabilité et gestion financière', 86),
('Service Après-Vente', 'Qualité du SAV', 84),
('Marketing et Ventes', 'Stratégies marketing et performance commerciale', 79);

-- ========================
-- 8. Link Audits with Categories
-- ========================

INSERT INTO audit_categories (audit_id, categorie_id) VALUES
(1, 1), (1, 2), (1, 6),
(2, 4), (2, 5), (2, 7),
(3, 1), (3, 8), (3, 10),
(4, 2), (4, 3), (4, 4),
(5, 1), (5, 5), (5, 9),
(6, 7), (6, 8), (6, 10);

-- ========================
-- 9. Insert External Links
-- ========================

INSERT INTO liens_externes (url, code_acces, expires_at, utilise, telephone, administrateur_id, magasin_id, auditeur_externe_id) VALUES
('https://audit.qualite.ma/casablanca-2026', 'CAS001', '2026-12-31 23:59:59', false, '0612345678', 1, 1, NULL),
('https://audit.qualite.ma/rabat-2026', 'RAB002', '2026-12-31 23:59:59', false, '0623456789', 2, 5, NULL),
('https://audit.qualite.ma/marrakech-2026', 'MAR003', '2026-12-31 23:59:59', true, '0634567890', 1, 8, 1),
('https://audit.qualite.ma/tanger-2026', 'TNG004', '2026-12-31 23:59:59', false, '0645678901', 3, 13, NULL),
('https://audit.qualite.ma/fes-2026', 'FEZ005', '2026-12-31 23:59:59', false, '0656789012', 2, 11, 2),
('https://audit.qualite.ma/agadir-2026', 'AGA006', '2026-12-31 23:59:59', false, '0667890123', 4, 16, NULL),
('https://audit.qualite.ma/oujda-2026', 'OUJ007', '2026-12-31 23:59:59', false, '0678901234', 1, 18, NULL);

-- ========================
-- 10. Insert Attachments
-- ========================

INSERT INTO pieces_jointes (url, type, taille, uploaded_at, categorie_id) VALUES
('/uploads/rapport_audit_casablanca.pdf', 'PDF', 1048576, '2026-01-20 15:00:00', 1),
('/uploads/checklist_rabat.pdf', 'PDF', 524288, '2026-02-15 11:00:00', 4),
('/uploads/photo_magasin_marrakech.jpg', 'JPG', 2097152, '2026-03-28 16:30:00', 2),
('/uploads/notes_formulaire_tanger.xlsx', 'XLSX', 262144, '2026-02-28 12:00:00', 3),
('/uploads/audit_mars_2026_fes.pdf', 'PDF', 3145728, '2026-03-20 10:00:00', 1),
('/uploads/recommandations_agadir.docx', 'DOCX', 1572864, '2026-04-10 14:00:00', 5),
('/uploads/rapport_final_oujda.pdf', 'PDF', 2097152, '2026-07-25 16:00:00', 2),
('/uploads/audit_qualite_casa.pdf', 'PDF', 1572864, '2026-02-05 15:30:00', 1);

-- ========================
-- Display statistics
-- ========================
SELECT '=== Base de données Marocaine Initialisée ===' AS Status;
SELECT COUNT(*) AS Total_Utilisateurs FROM utilisateurs;
SELECT COUNT(*) AS Total_Magasins FROM magasins;
SELECT COUNT(*) AS Total_Missions FROM missions;
SELECT COUNT(*) AS Total_Audits FROM audits;
SELECT COUNT(*) AS Total_Categories FROM categories;
SELECT COUNT(*) AS Total_Regions FROM regions;

-- Display Moroccan regions
SELECT '=== Régions du Maroc ===' AS Title;
SELECT nom, code FROM regions ORDER BY nom;

-- Display stores by city
SELECT '=== Magasins par Ville ===' AS Title;
SELECT ville, COUNT(*) AS Nombre_Magasins FROM magasins GROUP BY ville ORDER BY Nombre_Magasins DESC;