export const initialUsers = [
    { id: 1, firstName: "Liam", lastName: "Turner", email: "liam.turner@example.com", role: "admin" },
    { id: 2, firstName: "Sofia", lastName: "Petrov", email: "sofia.petrov@example.com", role: "auditor" },
    { id: 3, firstName: "Noah", lastName: "Keller", email: "noah.keller@example.com", role: "auditor" },
    { id: 4, firstName: "Maya", lastName: "Rossi", email: "maya.rossi@example.com", role: "auditor" },
    { id: 5, firstName: "Ethan", lastName: "Bauer", email: "ethan.bauer@example.com", role: "auditor" },
    { id: 6, firstName: "Lina", lastName: "Novak", email: "lina.novak@example.com", role: "auditor" },
    { id: 7, firstName: "Lucas", lastName: "Meier", email: "lucas.meier@example.com", role: "admin" },
    { id: 8, firstName: "Eva", lastName: "Horvat", email: "eva.horvat@example.com", role: "auditor" },
    { id: 9, firstName: "Daniel", lastName: "Ionescu", email: "daniel.ionescu@example.com", role: "auditor" },
    { id: 10, firstName: "Nina", lastName: "Kovac", email: "nina.kovac@example.com", role: "auditor" },
    { id: 11, firstName: "Oliver", lastName: "Larsen", email: "oliver.larsen@example.com", role: "auditor" },
    { id: 12, firstName: "Clara", lastName: "Silva", email: "clara.silva@example.com", role: "admin" },
    { id: 13, firstName: "Hugo", lastName: "Andersson", email: "hugo.andersson@example.com", role: "auditor" },
    { id: 14, firstName: "Anna", lastName: "Fischer", email: "anna.fischer@example.com", role: "auditor" },
    { id: 15, firstName: "Leo", lastName: "Nowak", email: "leo.nowak@example.com", role: "auditor" },
    { id: 16, firstName: "Mila", lastName: "Peterson", email: "mila.peterson@example.com", role: "auditor" },
    { id: 17, firstName: "Felix", lastName: "Schmidt", email: "felix.schmidt@example.com", role: "admin" },
    { id: 18, firstName: "Iris", lastName: "Dubois", email: "iris.dubois@example.com", role: "auditor" },
    { id: 19, firstName: "Jonas", lastName: "Weber", email: "jonas.weber@example.com", role: "auditor" },
    { id: 20, firstName: "Elena", lastName: "Marin", email: "elena.marin@example.com", role: "admin" },
];

export const initialRegions = [
    { id: 1, nom: "Casablanca-Settat", code: "20000 - 28999" },
    { id: 2, nom: "Rabat-Salé-Kénitra", code: "10000 - 14999" },
    { id: 3, nom: "Marrakech-Safi", code: "40000 - 46999" },
    { id: 4, nom: "Fès-Meknès", code: "30000 - 35999"},
    { id: 5, nom: "Tanger-Tétouan-Al Hoceïma", code: "90000 - 93999" },
    { id: 6, nom: "Souss-Massa", code: "80000 - 85999" },
    { id: 7, nom: "Oriental", code: "60000 - 64999" },
    { id: 8, nom: "Béni Mellal-Khénifra", code: "23000 - 25999" },
    { id: 9, nom: "Drâa-Tafilalet", code: "45000 - 47999" },
    { id: 10, nom: "Laâyoune-Sakia El Hamra", code: "70000 - 72999" },
    { id: 11, nom: "Dakhla-Oued Ed-Dahab", code: "73000 - 73999" },
    { id: 12, nom: "Guelmim-Oued Noun", code: "81000 - 82999" },
]


export const initialMissions = [
    { id: 1, titre: "Audit Qualité Q1", magasin: "ElectroPlanet Casablanca Maarif", auditeur: "Karim Benali", dateDebut: "2024-01-15", dateFin: "2024-01-20", statut: "Terminée" },
    { id: 2, titre: "Contrôle Stock Hiver", magasin: "ElectroPlanet Rabat Agdal", auditeur: "Fatima Zahra Idrissi", dateDebut: "2024-02-01", dateFin: "2024-02-05", statut: "En cours" },
    { id: 3, titre: "Inspection Sécurité", magasin: "ElectroPlanet Marrakech Guéliz", auditeur: null, dateDebut: "2024-02-10", dateFin: "2024-02-15", statut: "En attente" },
    { id: 4, titre: "Audit Service Client", magasin: "ElectroPlanet Fès Atlas", auditeur: "Youssef Tazi", dateDebut: "2024-02-20", dateFin: "2024-02-25", statut: "En cours" },
    { id: 5, titre: "Vérification Affichage Prix", magasin: "ElectroPlanet Tanger Ibn Batouta", auditeur: null, dateDebut: "2024-03-01", dateFin: "2024-03-03", statut: "En attente" },
    { id: 6, titre: "Audit Hygiène", magasin: "ElectroPlanet Agadir Talborjt", auditeur: "Zineb Alaoui", dateDebut: "2024-03-05", dateFin: "2024-03-08", statut: "Terminée" },
    { id: 7, titre: "Contrôle Merchandising", magasin: "ElectroPlanet Meknès Hamria", auditeur: "Omar Bensouda", dateDebut: "2024-03-10", dateFin: "2024-03-12", statut: "En cours" },
    { id: 8, titre: "Audit Personnel", magasin: "ElectroPlanet Oujda Centre", auditeur: null, dateDebut: "2024-03-15", dateFin: "2024-03-18", statut: "En attente" },
    { id: 9, titre: "Inspection Façade", magasin: "ElectroPlanet Kénitra Hay Riad", auditeur: "Nadia Chraibi", dateDebut: "2024-03-20", dateFin: "2024-03-22", statut: "Terminée" },
    { id: 10, titre: "Audit Expérience Client", magasin: "ElectroPlanet Rabat Agdal", auditeur: "Karim Benali", dateDebut: "2024-03-25", dateFin: "2024-03-28", statut: "En cours" },
    { id: 11, titre: "Contrôle Stock Printemps", magasin: "ElectroPlanet Casablanca Maarif", auditeur: "Fatima Zahra Idrissi", dateDebut: "2024-04-01", dateFin: "2024-04-03", statut: "En attente" },
    { id: 12, titre: "Audit Sécurité Incendie", magasin: "ElectroPlanet Marrakech Guéliz", auditeur: "Youssef Tazi", dateDebut: "2024-04-05", dateFin: "2024-04-07", statut: "Terminée" },
]


export const initialMagasins = [
  { "id": 1, "nom": "Electroplanet Casablanca - Ain Chok Technopark", "adresse": "Parc d'activité Marjane Californie Technopark", "ville": "Casablanca", "region": "Casablanca-Settat", "actif": true },
  { "id": 2, "nom": "Electroplanet Casablanca - Ain Sebaa", "adresse": "Route de Chabbou, Ain Sebaa", "ville": "Casablanca", "region": "Casablanca-Settat", "actif": true },
  { "id": 3, "nom": "Electroplanet Casablanca - Morocco Mall", "adresse": "Boulevard de Biarritz, Morocco Mall", "ville": "Casablanca", "region": "Casablanca-Settat", "actif": true },
  { "id": 4, "nom": "Electroplanet Casablanca - Marina", "adresse": "Marina Shopping Center", "ville": "Casablanca", "region": "Casablanca-Settat", "actif": true },
  { "id": 5, "nom": "Electroplanet Casablanca - Californie", "adresse": "Zone Marjane Californie", "ville": "Casablanca", "region": "Casablanca-Settat", "actif": false },

  { "id": 6, "nom": "Electroplanet Rabat - Agdal", "adresse": "Avenue de France, Agdal", "ville": "Rabat", "region": "Rabat-Salé-Kénitra", "actif": true },
  { "id": 7, "nom": "Electroplanet Rabat - Hay Riad", "adresse": "Avenue Annakhil, Hay Riad", "ville": "Rabat", "region": "Rabat-Salé-Kénitra", "actif": true },
  { "id": 8, "nom": "Electroplanet Salé", "adresse": "Boulevard Hassan II", "ville": "Salé", "region": "Rabat-Salé-Kénitra", "actif": true },
  { "id": 9, "nom": "Electroplanet Kénitra", "adresse": "Avenue Mohamed V", "ville": "Kénitra", "region": "Rabat-Salé-Kénitra", "actif": true },

  { "id": 10, "nom": "Electroplanet Marrakech - Menara", "adresse": "Route de Casablanca, Menara", "ville": "Marrakech", "region": "Marrakech-Safi", "actif": true },
  { "id": 11, "nom": "Electroplanet Marrakech - Marjane", "adresse": "Route de Casablanca, Marjane", "ville": "Marrakech", "region": "Marrakech-Safi", "actif": false },
  { "id": 12, "nom": "Electroplanet Safi", "adresse": "Route de Marrakech", "ville": "Safi", "region": "Marrakech-Safi", "actif": true },

  { "id": 13, "nom": "Electroplanet Fès", "adresse": "Route Sefrou, Marjane", "ville": "Fès", "region": "Fès-Meknès", "actif": true },
  { "id": 14, "nom": "Electroplanet Meknès", "adresse": "Route Fès", "ville": "Meknès", "region": "Fès-Meknès", "actif": true },

  { "id": 15, "nom": "Electroplanet Tanger - Socco Alto", "adresse": "Centre Commercial Socco Alto", "ville": "Tanger", "region": "Tanger-Tétouan-Al Hoceima", "actif": true },
  { "id": 16, "nom": "Electroplanet Tétouan", "adresse": "Avenue Abdelkhalek Torres", "ville": "Tétouan", "region": "Tanger-Tétouan-Al Hoceima", "actif": true },
  { "id": 17, "nom": "Electroplanet Nador", "adresse": "Route principale Nador", "ville": "Nador", "region": "Oriental", "actif": true },
  { "id": 18, "nom": "Electroplanet Oujda", "adresse": "Boulevard Mohamed V", "ville": "Oujda", "region": "Oriental", "actif": true },

  { "id": 19, "nom": "Electroplanet Agadir", "adresse": "Zone industrielle Tassila", "ville": "Agadir", "region": "Souss-Massa", "actif": true },

  { "id": 20, "nom": "Electroplanet El Jadida", "adresse": "Route de Casablanca", "ville": "El Jadida", "region": "Casablanca-Settat", "actif": true },
  { "id": 21, "nom": "Electroplanet Mohammedia", "adresse": "Boulevard Hassan II", "ville": "Mohammedia", "region": "Casablanca-Settat", "actif": false },
  { "id": 22, "nom": "Electroplanet Settat", "adresse": "Route de Casablanca", "ville": "Settat", "region": "Casablanca-Settat", "actif": true },

  { "id": 23, "nom": "Electroplanet Errachidia", "adresse": "Centre ville", "ville": "Errachidia", "region": "Drâa-Tafilalet", "actif": false }
]

export const initialElements = [
    { id: 1, nom: "Propreté du sol", description: "Vérifier l'état de propreté du sol dans tout le magasin", statut: "Actif" },
    { id: 2, nom: "Qualité de l'éclairage", description: "Vérifier que l'éclairage est suffisant et fonctionnel", statut: "Actif" },
    { id: 3, nom: "État du parking", description: "Vérifier la propreté et l'organisation du parking", statut: "Actif" },
    { id: 4, nom: "Façade du magasin", description: "Vérifier l'état de la façade et de l'enseigne", statut: "Inactif" },
    { id: 5, nom: "Disposition des articles", description: "Vérifier la mise en place et l'organisation des produits", statut: "Actif" },
    { id: 6, nom: "Mise en place des promotions", description: "Vérifier l'affichage et la visibilité des offres promotionnelles", statut: "Actif" },
    { id: 7, nom: "Tenue vestimentaire du personnel", description: "Vérifier que le personnel porte la tenue réglementaire", statut: "Actif" },
    { id: 8, nom: "Proactivité du personnel", description: "Évaluer la capacité du personnel à aborder les clients", statut: "Inactif" },
    { id: 9, nom: "File d'attente en caisse", description: "Vérifier la gestion des files d'attente aux caisses", statut: "Actif" },
    { id: 10, nom: "Conseil vendeur", description: "Évaluer la qualité des conseils donnés par les vendeurs", statut: "Actif" },
]


export const user = {
  name: "assia dahir",
  email: "dahirassia@gmail.com",
  role : "Administrateur"
}