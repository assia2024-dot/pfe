import api from "@/lib/axios"

export const userService = {
  getFiltered: (params) =>
    api.get("/api/users/filtered", { params }),

  create: (data) =>
    api.post("/api/users", data),

  update: (id, data) =>
    api.put(`/api/users/${id}`, data),

  toggleActif: (id, actif) =>
    api.patch(`/api/users/${id}/actif?actif=${actif}`),

  delete: (id) =>
    api.delete(`/api/users/${id}`),
}