/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.singulariti.os.ephemeris;

import com.singulariti.os.ephemeris.domain.MoonPosition;
import com.singulariti.os.ephemeris.domain.Observatory;
import com.singulariti.os.ephemeris.domain.Place;
import com.singulariti.os.ephemeris.domain.Planet;
import com.singulariti.os.ephemeris.domain.PlanetPosition;
import com.singulariti.os.ephemeris.domain.Pole;
import com.singulariti.os.ephemeris.domain.Star;
import com.singulariti.os.ephemeris.domain.StarPosition;
import com.singulariti.os.ephemeris.domain.SunPosition;
import com.singulariti.os.ephemeris.utils.FormatUtils;
import com.singulariti.os.ephemeris.utils.PlanetCatalog;
import com.singulariti.os.ephemeris.utils.StarCatalog;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 *
 * @author John
 */
public class Application {

    public Application() {
    }

    public void generateSunEphemeris(Observatory obs, ZonedDateTime start, ZonedDateTime end) {
        SunPositionCalculator sun = new SunPositionCalculator();
        List<SunPosition> ephemerides = sun.getEphemeris(obs, start, end, 10);
        printSunEphemeris(ephemerides);
    }

    void printSunEphemeris(List<SunPosition> ephs) {
        System.out.println(SunPosition.header());
        ephs.stream().forEach(e -> System.out.println(e));
    }

    public void generatePlanetEphemeris(Planet planet, Observatory obs, ZonedDateTime start, ZonedDateTime end) {
        PlanetPositionCalculator planetCalc = new PlanetPositionCalculator();
        List<PlanetPosition> ephemerides = planetCalc.getEphemeris(planet, obs, start, end, 10);
        printPlanetEphemeris(ephemerides);
    }

    void printPlanetEphemeris(List<PlanetPosition> ephs) {
        System.out.println(PlanetPosition.header());
        ephs.stream().forEach(e -> System.out.println(e));
    }

    public void generateMoonEphemeris(Observatory obs, ZonedDateTime start, ZonedDateTime end) {
        MoonPositionCalculator moonCalc = new MoonPositionCalculator();
        List<MoonPosition> ephemerides = moonCalc.getEphemeris(obs, start, end, 10);
        printMoonEphemeris(ephemerides);
    }

    void printMoonEphemeris(List<MoonPosition> ephs) {
        System.out.println(MoonPosition.header());
        ephs.stream().forEach(e -> System.out.println(e));
    }

    public void generateStarEphemeris(Star star, Observatory obs, ZonedDateTime start, ZonedDateTime end) {
        StarPositionCalculator starCalc = new StarPositionCalculator();
        List<StarPosition> ephemerides = starCalc.getEphemeris(star, obs, start, end, 10);
        printStarEphemeris(ephemerides);
    }

    void printStarEphemeris(List<StarPosition> ephs) {
        System.out.println(StarPosition.header());
        ephs.stream().forEach(e -> System.out.println(e));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Math.ceil((12 - 9) / 2.0));
        double latitude = 13.0068;
        double longitude = 76.0996;

        String strLat = FormatUtils.llstring(latitude);
        String strLng = FormatUtils.llstring(longitude);

        ZonedDateTime start = ZonedDateTime.of(2017, 11, 7, 0, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime end = start.plusHours(1);

        Place place = new Place("Hassan", latitude, Pole.NORTH, longitude, Pole.EAST, TimeZone.getTimeZone("Asia/Calcutta"), "", "");
        Observatory obs = new Observatory(place, start);

        System.out.println("Latitude: " + obs.getLatitude() + " - " + strLat);
        System.out.println("Longitude: " + obs.getLongitude() + " - " + strLng);

        Application application = new Application();

        System.out.println("=========================================================================================");
        System.out.println(" SUN");
        System.out.println("=========================================================================================");

        application.generateSunEphemeris(obs, start, end);

        System.out.println("=========================================================================================");
        System.out.println(" MARS");
        System.out.println("=========================================================================================");

        Planet mars = PlanetCatalog.byName("Mars");
        application.generatePlanetEphemeris(mars, obs, start, end);

        System.out.println("=========================================================================================");
        System.out.println(" MOON");
        System.out.println("=========================================================================================");

        application.generateMoonEphemeris(obs, start, end);

        System.out.println("=========================================================================================");
        System.out.println(" CAS A");
        System.out.println("=========================================================================================");

        Star casA = StarCatalog.byIdAndConstellation("a", "cas");
        if (casA != null) {
            application.generateStarEphemeris(casA, obs, start, end);
        }

    }

}
