import api from "@/lib/axios"

export const storeService = {
  // GET /api/stores/filtered?page=0&size=10&sortBy=nom&sortDir=asc&keyword=casa&actif=true
  getFiltered: (params) =>
    api.get("/api/stores/filtered", { params }),

  // GET /api/stores
  getAll: () =>
    api.get("/api/stores"),

  // GET /api/stores/:id
  getById: (id) =>
    api.get(`/api/stores/${id}`),

  // GET /api/stores/active
  getActive: () =>
    api.get("/api/stores/active"),

  // GET /api/stores/search?keyword=casa
  search: (keyword) =>
    api.get("/api/stores/search", { params: { keyword } }),

  // GET /api/stores/regions  → for dropdowns
  getRegions: () =>
    api.get("/api/stores/regions"),

  // GET /api/stores/villes  → for dropdowns
  getVilles: () =>
    api.get("/api/stores/villes"),

  // GET /api/stores/stats/regions  → BI
  getStatsByRegion: () =>
    api.get("/api/stores/stats/regions"),

  // POST /api/stores  (ADMIN only)
  create: (data) =>
    api.post("/api/stores", data),

  // PUT /api/stores/:id  (ADMIN only)
  update: (id, data) =>
    api.put(`/api/stores/${id}`, data),

  // DELETE /api/stores/:id  (ADMIN only)
  delete: (id) =>
    api.delete(`/api/stores/${id}`),
}