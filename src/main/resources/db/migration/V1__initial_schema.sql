CREATE TYPE USER_ROLE AS ENUM ('ROLE_PASSENGER', 'ROLE_CLERK', 'ROLE_ADMIN', 'ROLE_DRIVER',
    'ROLE_DISPATCHER', 'ROLE_SENDER', 'ROLE_RECEIVER');

CREATE TYPE BUS_STATUS AS ENUM ('AVAILABLE', 'IN_MAINTENANCE','ASSIGNED', 'OUT_OF_SERVICE');

CREATE TYPE TRIP_STATUS AS ENUM ('SCHEDULED', 'BOARDING', 'BOARDING_CLOSE','DEPARTED', 'ARRIVED', 'CANCELLED');

CREATE TYPE ENTITY_TYPE AS ENUM ('TRIP', 'TICKET', 'PARCEL', 'OFFLINE_SALE');

CREATE TYPE INCIDENT_TYPE AS ENUM ('SECURITY', 'DELIVERY_FAIL', 'OVERBOOK', 'VEHICLE', 'SEAT_TAKEN',
    'BUS_FULL', 'TRIP_DEPARTED');

CREATE TYPE SEAT_TYPE AS ENUM ('STANDARD', 'PREFERENTIAL');

CREATE TYPE SEAT_HOLD_STATUS AS ENUM ('HOLD', 'EXPIRED');

CREATE TYPE DISCOUNT_TYPE AS ENUM ('CHILD', 'STUDENT', 'OLDER_ADULT');

CREATE TYPE PARCEL_STATUS AS ENUM ('CREATED', 'IN_TRANSIT', 'READY_FOR_PICKUP', 'DELIVERED', 'FAILED');

CREATE TYPE TICKET_STATUS AS ENUM ('CREATED', 'SOLD', 'USED', 'CANCELLED', 'NO_SHOW');

CREATE TYPE SYNC_STATUS AS ENUM ('PENDING', 'SYNCING', 'SYNCED', 'CONFLICT', 'FAILED');

CREATE TYPE PAYMENT_METHOD AS ENUM ('CASH', 'QR', 'TRANSFER', 'CARD');

CREATE TABLE amenities
(
    amenity_id BIGSERIAL PRIMARY KEY,
    name       VARCHAR(30) NOT NULL
);

CREATE TABLE users
(
    user_id    BIGSERIAL PRIMARY KEY,
    full_name  VARCHAR(100)       NOT NULL,
    phone      VARCHAR(15) UNIQUE NOT NULL,
    created_at timestamptz        NOT NULL
);

CREATE TABLE app_users
(
    user_id       BIGINT PRIMARY KEY,
    email         VARCHAR(75) UNIQUE   NOT NULL,
    password_hash VARCHAR(16)          NOT NULL,
    active        BOOLEAN DEFAULT TRUE NOT NULL,
    CONSTRAINT fk_users_app_users FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE user_roles
(
    user_id BIGINT    NOT NULL,
    role    USER_ROLE NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES app_users (user_id) ON DELETE CASCADE
);

CREATE TABLE offline_users
(
    user_id   BIGINT PRIMARY KEY,
    id_number VARCHAR(10) UNIQUE NOT NULL,
    CONSTRAINT fk_users_offline_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE routes
(
    route_id     BIGSERIAL PRIMARY KEY,
    code         VARCHAR(10) UNIQUE NOT NULL,
    name         VARCHAR(100)       NOT NULL,
    origin       VARCHAR(50)        NOT NULL,
    destination  VARCHAR(50)        NOT NULL,
    distance_km  NUMERIC(6, 2)      NOT NULL,
    duration_min INTEGER            NOT NULL
);

CREATE TABLE buses
(
    bus_id   BIGSERIAL PRIMARY KEY,
    plate    VARCHAR(8) UNIQUE NOT NULL,
    capacity INTEGER           NOT NULL,
    status   BUS_STATUS        NOT NULL DEFAULT 'AVAILABLE'
);

CREATE TABLE bus_amenities
(
    bus_id     BIGINT NOT NULL,
    amenity_id BIGINT NOT NULL,
    PRIMARY KEY (bus_id, amenity_id),
    FOREIGN KEY (bus_id) REFERENCES buses (bus_id),
    FOREIGN KEY (amenity_id) REFERENCES amenities (amenity_id)
);

CREATE TABLE trips
(
    trip_id      BIGSERIAL PRIMARY KEY,
    route_id     BIGINT      NOT NULL,
    bus_id       BIGINT,
    date         DATE        NOT NULL,
    arrival_eta  timestamptz NOT NULL,
    departure_at timestamptz NOT NULL,
    status       TRIP_STATUS NOT NULL DEFAULT 'SCHEDULED',
    CONSTRAINT fk_routes_trips FOREIGN KEY (route_id) REFERENCES routes (route_id) ON DELETE CASCADE,
    CONSTRAINT fk_buses_trips FOREIGN KEY (bus_id) REFERENCES buses (bus_id)
);

CREATE TABLE assignments
(
    assignment_id BIGSERIAL PRIMARY KEY,
    trip_id       BIGINT      NOT NULL,
    driver_id     BIGINT      NOT NULL,
    dispatcher_id BIGINT      NOT NULL,
    check_list_Ok BOOLEAN     NOT NULL DEFAULT FALSE,
    assigned_at   timestamptz NOT NULL,
    CONSTRAINT fk_trips_assignments FOREIGN KEY (trip_id) REFERENCES trips (trip_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_users_assignments FOREIGN KEY (driver_id) REFERENCES app_users (user_id),
    CONSTRAINT fk_app_users_assignments FOREIGN KEY (dispatcher_id) REFERENCES app_users (user_id)
);

CREATE TABLE configs
(
    key   VARCHAR PRIMARY KEY,
    value NUMERIC(6, 2) NOT NULL
);

CREATE TABLE incidents
(
    incident_id BIGSERIAL PRIMARY KEY,
    entity_type ENTITY_TYPE            NOT NULL,
    entity_id   BIGINT                 NOT NULL,
    type        INCIDENT_TYPE          NOT NULL,
    created_at  timestamptz            NOT NULL,
    note        TEXT
);

CREATE TABLE seats
(
    seat_id BIGSERIAL PRIMARY KEY,
    bus_id  BIGINT     NOT NULL,
    number  VARCHAR(5) NOT NULL,
    type    SEAT_TYPE  NOT NULL DEFAULT 'STANDARD',
    CONSTRAINT fk_buses_seats FOREIGN KEY (bus_id) REFERENCES buses (bus_id) ON DELETE CASCADE
);

CREATE TABLE seats_hold
(
    seat_hold_id BIGSERIAL PRIMARY KEY,
    trip_id      BIGINT                 NOT NULL,
    seat_number  VARCHAR(5)             NOT NULL,
    passenger_id BIGINT                 NOT NULL,
    expires_at   timestamptz            NOT NULL,
    status       SEAT_HOLD_STATUS       NOT NULL DEFAULT 'HOLD',
    CONSTRAINT fk_trips_seats_hold FOREIGN KEY (trip_id) REFERENCES trips (trip_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_users_seats_hold FOREIGN KEY (passenger_id) REFERENCES app_users (user_id)
);

CREATE TABLE stops
(
    stop_id    BIGSERIAL PRIMARY KEY,
    route_id   BIGINT  NOT NULL,
    name       VARCHAR NOT NULL,
    stop_order INTEGER NOT NULL,
    latitude   NUMERIC(10, 7),
    longitude  NUMERIC(10, 7),
    CONSTRAINT fk_routes_stops FOREIGN KEY (route_id) REFERENCES routes (route_id) ON DELETE CASCADE
);

CREATE TABLE fare_rules
(
    fare_rule_id    BIGSERIAL PRIMARY KEY,
    route_id        BIGINT        NOT NULL,
    from_stop_id    BIGINT        NOT NULL,
    to_stop_id      BIGINT        NOT NULL,
    base_price      NUMERIC(8, 2) NOT NULL,
    dynamic_pricing BOOLEAN       NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_routes_fare_rules FOREIGN KEY (route_id) REFERENCES routes (route_id) ON DELETE CASCADE,
    CONSTRAINT fk_stops_fare_rules FOREIGN KEY (from_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_stops_fare_rules FOREIGN KEY (to_stop_id) REFERENCES stops (stop_id)
);

CREATE TABLE discounts
(
    discount_id  BIGSERIAL PRIMARY KEY,
    fare_rule_id BIGINT        NOT NULL,
    type         DISCOUNT_TYPE NOT NULL,
    value        NUMERIC(3, 2) NOT NULL,
    CONSTRAINT fk_fare_rules_discounts FOREIGN KEY (fare_rule_id) REFERENCES fare_rules (fare_rule_id) ON DELETE CASCADE
);

CREATE TABLE parcels
(
    parcel_id    BIGSERIAL PRIMARY KEY,
    code         VARCHAR(16) UNIQUE NOT NULL,
    from_stop_id BIGINT             NOT NULL,
    to_stop_id   BIGINT             NOT NULL,
    price        NUMERIC(8, 2)      NOT NULL,
    sender_id    BIGINT             NOT NULL,
    receiver_id  BIGINT             NOT NULL,
    status       PARCEL_STATUS      NOT NULL DEFAULT 'CREATED',
    delivery_otp VARCHAR(8) UNIQUE,
    CONSTRAINT fk_stops_parcels FOREIGN KEY (from_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_stops_parcels FOREIGN KEY (to_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_app_users_parcels FOREIGN KEY (sender_id) REFERENCES app_users (user_id),
    CONSTRAINT fk_app_users_parcels FOREIGN KEY (receiver_id) REFERENCES app_users (user_id)
);

CREATE TABLE tickets
(
    ticket_id      BIGSERIAL PRIMARY KEY,
    trip_id        BIGINT         NOT NULL,
    passenger_id   BIGINT         NOT NULL,
    seat_number    VARCHAR(5)     NOT NULL,
    from_stop_id   BIGINT         NOT NULL,
    to_stop_id     BIGINT         NOT NULL,
    price          NUMERIC(8, 2)  NOT NULL,
    payment_method PAYMENT_METHOD NOT NULL,
    status         TICKET_STATUS  NOT NULL DEFAULT 'CREATED',
    qr_code        VARCHAR UNIQUE,
    CONSTRAINT fk_stops_tickets FOREIGN KEY (from_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_stops_tickets FOREIGN KEY (to_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_trips_tickets FOREIGN KEY (trip_id) REFERENCES trips (trip_id),
    CONSTRAINT fk_users_tickets FOREIGN KEY (passenger_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE baggage
(
    baggage_id   BIGSERIAL PRIMARY KEY,
    ticket_id    BIGINT             NOT NULL,
    weight_kg    NUMERIC(5, 2)      NOT NULL,
    fee          NUMERIC(8, 2)      NOT NULL DEFAULT 0.0,
    tag_code     VARCHAR(14) UNIQUE NOT NULL,
    trunk_number INTEGER,
    CONSTRAINT fk_tickets_baggage FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id) ON DELETE CASCADE
);

CREATE TABLE offline_sales
(
    offline_sale_id     BIGSERIAL PRIMARY KEY,
    trip_id             BIGINT                 NOT NULL,
    seat_number         VARCHAR(5)             NOT NULL,
    from_stop_id        BIGINT                 NOT NULL,
    to_stop_id          BIGINT                 NOT NULL,
    passenger_id_number VARCHAR(10) UNIQUE     NOT NULL,
    passenger_name      VARCHAR,
    passenger_phone     VARCHAR(15) UNIQUE,
    price               NUMERIC(8, 2)          NOT NULL,
    payment_method      PAYMENT_METHOD         NOT NULL,
    status              SYNC_STATUS            NOT NULL DEFAULT 'PENDING',
    sync_attempts       INTEGER                NOT NULL DEFAULT 0,
    created_at          timestamptz            NOT NULL,
    synced_at           timestamptz,
    ticket_id           BIGINT,
    CONSTRAINT fk_trips_offline_sales FOREIGN KEY (trip_id) REFERENCES trips (trip_id) ON DELETE CASCADE,
    CONSTRAINT fk_stops_offline_sales FOREIGN KEY (from_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_stops_offline_sales FOREIGN KEY (to_stop_id) REFERENCES stops (stop_id),
    CONSTRAINT fk_tickets_offline_sales FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id) ON DELETE CASCADE
)