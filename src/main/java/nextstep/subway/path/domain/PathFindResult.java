package nextstep.subway.path.domain;

import java.util.List;
import java.util.Set;
import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;

/**
 * 경로 검색 결과를 리턴하는 도메인 객체 (도메인에서 DTO를 의존하는 상황을 피하기 위함)
 */
public class PathFindResult {
    private List<Station> stations;

    private Set<Line> lines;

    private int distance;

    private SubwayFare fare;

    protected PathFindResult() {

    }

    public PathFindResult(List<Station> stations, Set<Line> lines, int distance) {
        this.stations = stations;
        this.lines = lines;
        this.distance = distance;
        this.fare = calculateSubwayFareByDistance(distance);
    }

    private SubwayFare calculateSubwayFareByDistance(int distance){
        return SubwayFare.calculateByDistance(distance);
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public SubwayFare getFare() {
        return fare;
    }

    public Set<Line> getLines() {
        return lines;
    }
}
