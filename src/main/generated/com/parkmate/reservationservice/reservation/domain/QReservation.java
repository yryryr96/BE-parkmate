package com.parkmate.reservationservice.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -713603497L;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.parkmate.reservationservice.common.entity.QBaseEntity _super = new com.parkmate.reservationservice.common.entity.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> entryTime = createDateTime("entryTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> exitTime = createDateTime("exitTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parkingLotName = createString("parkingLotName");

    public final StringPath parkingLotUuid = createString("parkingLotUuid");

    public final NumberPath<Long> parkingSpotId = createNumber("parkingSpotId", Long.class);

    public final StringPath parkingSpotName = createString("parkingSpotName");

    public final StringPath reservationCode = createString("reservationCode");

    public final EnumPath<ReservationStatus> status = createEnum("status", ReservationStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath userUuid = createString("userUuid");

    public final StringPath vehicleNumber = createString("vehicleNumber");

    public QReservation(String variable) {
        super(Reservation.class, forVariable(variable));
    }

    public QReservation(Path<? extends Reservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservation(PathMetadata metadata) {
        super(Reservation.class, metadata);
    }

}

