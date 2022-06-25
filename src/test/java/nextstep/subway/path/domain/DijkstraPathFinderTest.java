package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class DijkstraPathFinderTest {

    private Station 강남역;
    private Station 양재역;
    private Station 교대역;
    private Station 남부터미널역;

    private Line 이호선;
    private Line 삼호선;
    private Line 신분당선;

    private List<Line> lines;

    /**
     * 교대역    --- *2호선* (10) ---    강남역         시청역
     * |                                  |             |
     * *3호선(5)*                     *신분당선* (6)   *1호선*(3)
     * |                                 |             |
     * 남부터미널역 --- *3호선 (3)* --- 양재역         종각역
     */

    @BeforeEach
    public void setUp() {
        강남역 = new Station(1L, "강남역");
        양재역 = new Station(2L, "양재역");
        교대역 = new Station(3L, "교대역");
        남부터미널역 = new Station(4L, "남부터미널역");

        이호선 = new Line("이호선", "bg-green-600", 교대역, 강남역, 10);
        신분당선 = new Line("신분당선", "bg-red-600", 강남역, 양재역, 6);
        삼호선 = new Line("삼호선", "bg-orange-600", 교대역, 남부터미널역, 5);

        삼호선.addSection(new Section(삼호선, 남부터미널역, 양재역, 3));

        lines = new ArrayList<>();
        lines.add(이호선);
        lines.add(삼호선);
        lines.add(신분당선);
    }

    @DisplayName("출발역과 도착역이 같은 경우 오류가 발생한다.")
    @Test
    void hasSameSourceAndTarget() {
        PathFinder pathFinder = new DijkstraPathFinder(lines);
        assertThrows(IllegalArgumentException.class, () -> pathFinder.findShortestPath(강남역, 강남역)
                , "출발역과 도착역이 동일합니다.");
    }

    @DisplayName("출발역과 도착역이 연결되어 있지 않은 경우 오류가 발생한다.")
    @Test
    void isNotConnected() {
        Station 시청역 = new Station(10L, "시청역");
        Station 종각역 = new Station(11L, "종각역");
        Line 일호선 = new Line("일호선", "bg-navy-600", 시청역, 종각역, 3);
        lines.add(일호선);

        PathFinder pathFinder = new DijkstraPathFinder(lines);

        assertThrows(IllegalArgumentException.class, () -> pathFinder.findShortestPath(강남역, 종각역)
                , "최단거리가 존재하지 않습니다.");
    }

}
