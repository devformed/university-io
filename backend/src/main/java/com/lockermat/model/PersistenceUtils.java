package com.lockermat.model;

import com.lockermat.model.dto.Position;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

/**
 * @author Anton Gorokh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PersistenceUtils {

	/**
	 * SRID for WGS 84 (World Geodetic System 1984).
	 * This is the standard for GPS and global latitude/longitude data.
	 * The authority is the European Petroleum Survey Group (EPSG).
	 *
	 * @see <a href="https://epsg.io/4326">https://epsg.io/4326</a>
	 */
	public static final int WGS_84 = 4326;

	public static final PrecisionModel GEOM_PRECISION_MODEL = new PrecisionModel();

	public static final GeometryFactory GEOM_FACTORY = new GeometryFactory(GEOM_PRECISION_MODEL, WGS_84);

	public static Point toPoint(Position position) {
		Coordinate coordinate = new Coordinate(
				position.longitude().doubleValue(),
				position.latitude().doubleValue()
		);
		return GEOM_FACTORY.createPoint(coordinate);
	}
}
