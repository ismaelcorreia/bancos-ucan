db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "conecta_db"
            }
        ]
    }
);
