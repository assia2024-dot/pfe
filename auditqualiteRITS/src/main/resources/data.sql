-- ========================
-- 1. Insert Utilisateurs
-- ========================

-- Administrateurs (role = ADMIN)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, actif) VALUES
                                                                             ('Alaoui',    'Youssef',  'youssef.alaoui@electroplanet.ma',   '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN',    true),
                                                                             ('Bennani',   'Salma',    'salma.bennani@electroplanet.ma',    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN',    true),
                                                                             ('Chraibi',   'Karim',    'karim.chraibi@electroplanet.ma',    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'ADMIN',    true);

INSERT INTO administrateurs (utilisateur_id) VALUES (1), (2), (3);

-- Auditeurs (role = AUDITEUR)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, actif) VALUES
                                                                             ('Idrissi',   'Hamid',    'hamid.idrissi@electroplanet.ma',    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Ouazzani',  'Nadia',    'nadia.ouazzani@electroplanet.ma',   '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Tazi',      'Rachid',   'rachid.tazi@electroplanet.ma',      '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Berrada',   'Fatima',   'fatima.berrada@electroplanet.ma',   '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Hajji',     'Omar',     'omar.hajji@electroplanet.ma',       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Moussaoui', 'Zineb',    'zineb.moussaoui@electroplanet.ma',  '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Kettani',   'Amine',    'amine.kettani@electroplanet.ma',    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true),
                                                                             ('Filali',    'Houda',    'houda.filali@electroplanet.ma',     '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIi2', 'AUDITEUR', true);

INSERT INTO auditeurs (utilisateur_id, telephone, region) VALUES
                                                              (4,  '0661234567', 'Casablanca-Settat'),
                                                              (5,  '0662345678', 'Rabat-Salé-Kénitra'),
                                                              (6,  '0663456789', 'Marrakech-Safi'),
                                                              (7,  '0664567890', 'Tanger-Tétouan-Al Hoceima'),
                                                              (8,  '0665678901', 'Fès-Meknès'),
                                                              (9,  '0666789012', 'Souss-Massa'),
                                                              (10, '0667890123', 'Oriental'),
                                                              (11, '0668901234', 'Casablanca-Settat');

-- ========================
-- 2. Insert Regions
-- ========================
INSERT INTO regions (nom, code) VALUES
                                    ('Casablanca-Settat',          'CAS'),   -- id 1
                                    ('Rabat-Salé-Kénitra',         'RSK'),   -- id 2
                                    ('Marrakech-Safi',             'MRS'),   -- id 3
                                    ('Fès-Meknès',                 'FMK'),   -- id 4
                                    ('Tanger-Tétouan-Al Hoceima',  'TTA'),   -- id 5
                                    ('Souss-Massa',                'SOM'),   -- id 6
                                    ('Oriental',                   'ORI'),   -- id 7
                                    ('Doukkala-Abda',              'DOA');   -- id 8

-- ========================
-- 3. Insert 45 Magasins Electroplanet réels
-- ========================
INSERT INTO magasins (nom, adresse, ville, actif, region_id) VALUES

-- CASABLANCA-SETTAT (14 magasins)
('Electroplanet Derb Sultan',       'Galerie Commerciale Marjane, Bv Mohammed VI',        'Casablanca',  true, 1),  -- 1
('Electroplanet Tachefine',         'Centre Commercial Tachefine Center',                 'Casablanca',  true, 1),  -- 2
('Electroplanet Technopark',        'Parc Marjane Californie, Technopark',                'Casablanca',  true, 1),  -- 3
('Electroplanet Sidi Maarouf',      'O''Village, Sidi Maarouf',                           'Casablanca',  true, 1),  -- 4
('Electroplanet Maarif',            'Centre Maarif, Bd Ghandi',                           'Casablanca',  true, 1),  -- 5
('Electroplanet Acima Inara',       'Centre Commercial Acima Inara',                      'Casablanca',  true, 1),  -- 6
('Electroplanet Sidi Othmane',      'Galerie Commerciale Marjane Sidi Othmane',           'Casablanca',  true, 1),  -- 7
('Electroplanet Mohammedia',        'Galerie Commerciale Marjane, Avenue des FAR',        'Mohammedia',  true, 1),  -- 8
('Electroplanet Berrechid',         'Galerie Commerciale Marjane Berrechid',              'Berrechid',   true, 1),  -- 9
('Electroplanet Settat',            'Galerie Commerciale Marjane Settat',                 'Settat',      true, 1),  -- 10
('Electroplanet Bouskoura',         'Galerie Commerciale Marjane Bouskoura',              'Bouskoura',   true, 1),  -- 11
('Electroplanet Hay Hassani',       'Galerie Commerciale Acima Hay Hassani',              'Casablanca',  true, 1),  -- 12
('Electroplanet Ain Sebaa',         'Galerie Commerciale Marjane Ain Sebaa',              'Casablanca',  true, 1),  -- 13
('Electroplanet Ouled Salah',       'Galerie Commerciale Marjane Ouled Salah',            'Casablanca',  true, 1),  -- 14

-- RABAT-SALÉ-KÉNITRA (7 magasins)
('Electroplanet Hay Riad',          'Galerie Commerciale Marjane Hay Riad, Autoroute Rabat-Tanger', 'Rabat', true, 2),  -- 15
('Electroplanet Rabat Hassan',      'Avenue Hassan II, Lotissement Vita',                 'Rabat',       true, 2),  -- 16
('Electroplanet Témara',            'Galerie Commerciale Marjane Témara',                 'Témara',      true, 2),  -- 17
('Electroplanet Salé',              'Galerie Commerciale Marjane Salé',                   'Salé',        true, 2),  -- 18
('Electroplanet Kénitra',           'Galerie Commerciale Marjane Kénitra',                'Kénitra',     true, 2),  -- 19
('Electroplanet Kénitra Centre',    'Avenue Mohammed Diouri, Centre Ville',               'Kénitra',     true, 2),  -- 20
('Electroplanet Skhirat',           'Galerie Commerciale Marjane Skhirat',                'Skhirat',     true, 2),  -- 21

-- MARRAKECH-SAFI (5 magasins)
('Electroplanet Guéliz',            'Avenue Abdelkarim Khattabi, Guéliz',                 'Marrakech',   true, 3),  -- 22
('Electroplanet Ménara',            'Parc Marjane Square, Route de Casablanca',           'Marrakech',   true, 3),  -- 23
('Electroplanet Daoudiate',         'Route de Casablanca, Daoudiate',                     'Marrakech',   true, 3),  -- 24
('Electroplanet Safi',              'Galerie Commerciale Marjane Safi',                   'Safi',        true, 3),  -- 25
('Electroplanet Essaouira',         'Avenue de l''Istiqlal, Centre Ville',                'Essaouira',   true, 3),  -- 26

-- FÈS-MEKNÈS (5 magasins)
('Electroplanet Fès Saïss',         'Parc Marjane Saïss, Route d''Imouzzer',              'Fès',         true, 4),  -- 27
('Electroplanet Fès Centre',        'Avenue Hassan II, Ville Nouvelle',                   'Fès',         true, 4),  -- 28
('Electroplanet Meknès',            'Galerie Commerciale Marjane Meknès',                 'Meknès',      true, 4),  -- 29
('Electroplanet Meknès Centre',     'Avenue Mohammed V, Centre Ville',                    'Meknès',      true, 4),  -- 30
('Electroplanet Ifrane',            'Avenue Hassan II, Centre Ville',                     'Ifrane',      true, 4),  -- 31

-- TANGER-TÉTOUAN-AL HOCEIMA (5 magasins)
('Electroplanet Tanger Médina',     'Parc Marjane Médina, Route de Rabat Km 6,2',         'Tanger',      true, 5),  -- 32
('Electroplanet Tanger City Mall',  'Tanger City Mall, Route Tanja El Balia',             'Tanger',      true, 5),  -- 33
('Electroplanet Tétouan',           'Galerie Commerciale Marjane Tétouan',                'Tétouan',     true, 5),  -- 34
('Electroplanet Al Hoceima',        'Avenue Mohammed V, Centre Ville',                    'Al Hoceima',  true, 5),  -- 35
('Electroplanet Larache',           'Galerie Commerciale Marjane Larache',                'Larache',     true, 5),  -- 36

-- SOUSS-MASSA (4 magasins)
('Electroplanet Agadir Souss',      'Souss Mall, Hay Mohammadi',                          'Agadir',      true, 6),  -- 37
('Electroplanet Agadir Centre',     'Galerie Commerciale Marjane Agadir',                 'Agadir',      true, 6),  -- 38
('Electroplanet Inezgane',          'Galerie Commerciale Marjane Inezgane',               'Inezgane',    true, 6),  -- 39
('Electroplanet Taroudant',         'Avenue Mohammed V, Centre Ville',                    'Taroudant',   true, 6),  -- 40

-- ORIENTAL (3 magasins)
('Electroplanet Oujda',             'Galerie Commerciale Marjane Oujda',                  'Oujda',       true, 7),  -- 41
('Electroplanet Nador',             'Galerie Commerciale Marjane Nador, Bni Drar',        'Nador',       true, 7),  -- 42
('Electroplanet Berkane',           'Galerie Commerciale Marjane Berkane',                'Berkane',     true, 7),  -- 43

-- DOUKKALA-ABDA (2 magasins)
('Electroplanet El Jadida',         'Galerie Commerciale Marjane El Jadida',              'El Jadida',   true, 8),  -- 44
('Electroplanet Safi Centre',       'Avenue Président Kennedy, Centre Ville',             'Safi',        true, 8);  -- 45

-- ========================
-- 4. Insert Missions
-- ========================
INSERT INTO missions (titre, date_debut, date_fin, statut, type, auditeur_id, administrateur_id, magasin_id) VALUES
                                                                                                                 ('Audit Qualité Derb Sultan',        '2026-01-10', '2026-01-20', 'COMPLETED', 'INTERNE', 4,  1, 1),
                                                                                                                 ('Audit Sécurité Hay Riad',          '2026-02-05', '2026-02-15', 'EN_COURS',  'INTERNE', 5,  2, 15),
                                                                                                                 ('Audit Conformité Guéliz',          '2026-03-01', '2026-03-10', 'PLANIFIEE', 'EXTERNE', 6,  1, 22),
                                                                                                                 ('Audit Performance Tanger Médina',  '2026-04-10', '2026-04-20', 'PLANIFIEE', 'INTERNE', 7,  3, 32),
                                                                                                                 ('Audit Environnemental Fès Saïss',  '2026-05-01', '2026-05-15', 'PLANIFIEE', 'EXTERNE', 8,  2, 27),
                                                                                                                 ('Audit Financier Sidi Maarouf',     '2026-03-15', '2026-03-25', 'EN_COURS',  'INTERNE', 4,  1, 4),
                                                                                                                 ('Audit Social Agadir Souss',        '2026-04-05', '2026-04-18', 'PLANIFIEE', 'EXTERNE', 9,  3, 37),
                                                                                                                 ('Audit Technique El Jadida',        '2026-02-20', '2026-02-28', 'COMPLETED', 'INTERNE', 6,  2, 44),
                                                                                                                 ('Audit Stock Kénitra',              '2026-05-10', '2026-05-20', 'PLANIFIEE', 'INTERNE', 7,  1, 19),
                                                                                                                 ('Audit Informatique Oujda',         '2026-06-01', '2026-06-10', 'PLANIFIEE', 'EXTERNE', 10, 3, 41),
                                                                                                                 ('Audit Hygiène Meknès',             '2026-06-15', '2026-06-25', 'PLANIFIEE', 'INTERNE', 8,  2, 29),
                                                                                                                 ('Audit Matériel Tétouan',           '2026-07-01', '2026-07-10', 'PLANIFIEE', 'INTERNE', 11, 1, 34);

-- ========================
-- 5. Insert Auditeurs Externes
-- ========================
INSERT INTO auditeurs_externes (nom, telephone, statut, date_acces, mission_id) VALUES
                                                                                    ('Cabinet Fidaroc Grant Thornton', '0522274747', 'ACTIF',   '2026-03-01 09:00:00', 3),
                                                                                    ('Cabinet Deloitte Maroc',         '0522777700', 'ACTIF',   '2026-05-01 10:00:00', 5),
                                                                                    ('Cabinet Ernst & Young Maroc',    '0522959595', 'ACTIF',   '2026-04-05 11:00:00', 7),
                                                                                    ('Cabinet KPMG Maroc',             '0522468686', 'INACTIF', NULL,                  NULL),
                                                                                    ('Cabinet PwC Maroc',              '0522952000', 'ACTIF',   '2026-06-01 09:00:00', 10);

-- ========================
-- 6. Insert Audits
-- ========================
INSERT INTO audits (date_realisation, note_globale, statut, commentaire, mission_id, auditeur_id, magasin_id) VALUES
                                                                                                                  ('2026-01-20 14:30:00', 87.5, 'COMPLETED', 'Bon résultat, quelques améliorations sur la gestion stock',       1, 4, 1),
                                                                                                                  ('2026-02-15 10:00:00', NULL, 'EN_COURS',  'Analyse en cours, documents en attente de validation',            2, 5, 15),
                                                                                                                  ('2026-03-25 16:00:00', 91.0, 'COMPLETED', 'Excellente conformité, personnel bien formé',                     6, 4, 4),
                                                                                                                  ('2026-02-28 11:30:00', 76.0, 'COMPLETED', 'Amélioration nécessaire sur la documentation et la sécurité',     8, 6, 44);

-- ========================
-- 7. Insert Categories (SANS audit_id — relation ManyToMany)
-- ========================
INSERT INTO categories (nom, description, note) VALUES
                                                    ('Qualité Service',  'Évaluation de la qualité du service client',       88),
                                                    ('Sécurité',         'Conformité aux normes de sécurité incendie',        90),
                                                    ('Hygiène',          'Propreté et hygiène des espaces de vente',          85),
                                                    ('Documentation',    'Gestion des documents administratifs et contrats',  72),
                                                    ('Formation',        'Niveau de formation du personnel de vente',         80),
                                                    ('Matériel',         'État et maintenance des équipements exposés',        83),
                                                    ('Informatique',     'Systèmes de caisse et logiciels de gestion',        78),
                                                    ('Stock',            'Gestion des stocks et inventaires produits',        88);

-- ========================
-- 8. Table de jointure audit_categories (ManyToMany)
-- ========================
INSERT INTO audit_categories (audit_id, categorie_id) VALUES
                                                          (1, 1), (1, 2), (1, 3),   -- Audit Derb Sultan    → Qualité Service, Sécurité, Hygiène
                                                          (2, 4), (2, 5),            -- Audit Hay Riad       → Documentation, Formation
                                                          (3, 5), (3, 6), (3, 8),   -- Audit Sidi Maarouf   → Formation, Matériel, Stock
                                                          (4, 4), (4, 7);            -- Audit El Jadida      → Documentation, Informatique

-- ========================
-- 9. Insert Liens Externes
-- ========================
INSERT INTO liens_externes (url, code_acces, expires_at, utilise, telephone, administrateur_id, magasin_id, auditeur_externe_id) VALUES
                                                                                                                                     ('https://audit.electroplanet.ma/gueliz2026',       'EP-ACC-001', '2026-12-31 23:59:59', false, '0663456789', 1, 22, 1),
                                                                                                                                     ('https://audit.electroplanet.ma/fes2026',          'EP-ACC-002', '2026-12-31 23:59:59', false, '0665678901', 2, 27, 2),
                                                                                                                                     ('https://audit.electroplanet.ma/agadir2026',       'EP-ACC-003', '2026-12-31 23:59:59', false, '0669012345', 3, 37, 3),
                                                                                                                                     ('https://audit.electroplanet.ma/tanger2026',       'EP-ACC-004', '2026-12-31 23:59:59', true,  '0664567890', 1, 32, NULL),
                                                                                                                                     ('https://audit.electroplanet.ma/oujda2026',        'EP-ACC-005', '2026-12-31 23:59:59', false, '0665678901', 2, 41, 5);

-- ========================
-- 10. Insert Pieces Jointes
-- ========================
INSERT INTO pieces_jointes (url, type, taille, uploaded_at, categorie_id) VALUES
                                                                              ('/uploads/rapport_derbsultan_jan2026.pdf',    'PDF',  1048576, '2026-01-20 15:00:00', 1),
                                                                              ('/uploads/checklist_securite_derbsultan.pdf', 'PDF',   524288, '2026-01-20 15:30:00', 2),
                                                                              ('/uploads/photo_hygiene_derbsultan.jpg',      'JPG',  2097152, '2026-01-21 09:00:00', 3),
                                                                              ('/uploads/docs_eljadida_fev2026.pdf',         'PDF',   786432, '2026-02-28 12:00:00', 4),
                                                                              ('/uploads/rapport_sidimaarouf_mar2026.pdf',   'PDF',  3145728, '2026-03-25 17:00:00', 5),
                                                                              ('/uploads/materiel_sidimaarouf.xlsx',         'XLSX',  262144, '2026-03-25 17:30:00', 6),
                                                                              ('/uploads/informatique_eljadida.docx',        'DOCX', 1572864, '2026-03-01 10:00:00', 7),
                                                                              ('/uploads/stock_sidimaarouf_mar2026.pdf',     'PDF',   943718, '2026-03-25 18:00:00', 8);
