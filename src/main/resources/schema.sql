-- drop table if exists requests cascade;
-- drop table if exists bookings cascade;
-- drop table if exists comments cascade;
-- drop table if exists items cascade;
-- drop table if exists users cascade;
CREATE TABLE IF NOT EXISTS users (
    id    INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS requests (
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description  VARCHAR(255) NOT NULL,
    requestor_id INTEGER NOT NULL REFERENCES users (id),
    CONSTRAINT pk_request PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_requestor ON requests(requestor_id);

CREATE TABLE IF NOT EXISTS items (
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL,
    owner_id     INTEGER NOT NULL REFERENCES users (id),
    request_id   INTEGER NULL REFERENCES requests (id),
    CONSTRAINT pk_item PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_owner ON items(owner_id);

CREATE TABLE IF NOT EXISTS bookings (
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id      INTEGER NOT NULL REFERENCES items (id),
    booker_id    INTEGER NOT NULL REFERENCES users (id),
    status       INTEGER NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_item ON bookings(item_id);
CREATE INDEX IF NOT EXISTS idx_booker ON bookings(booker_id);

CREATE TABLE IF NOT EXISTS comments (
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text         VARCHAR(512) NOT NULL,
    item_id      INTEGER NOT NULL REFERENCES items (id),
    author_id    INTEGER NOT NULL REFERENCES users (id),
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_item ON comments(item_id);