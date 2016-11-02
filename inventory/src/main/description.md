

@GET
  - All
    - `/items`
  - One
    - `/item/{_id: \\d+}`
      - @PathParam

@POST
  - Create
    - `/create`
      - @FormParam

@PUT
  - Update
    - `/update`
      - @FormParam

@DELETE
  - Remove
    - `/delete/{id: \\d+}`
      - @PathParam