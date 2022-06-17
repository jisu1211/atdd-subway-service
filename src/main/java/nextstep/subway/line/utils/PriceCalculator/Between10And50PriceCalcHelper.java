package nextstep.subway.line.utils.PriceCalculator;

import java.util.List;
import nextstep.subway.line.domain.Line;

public class Between10And50PriceCalcHelper implements PriceCalcHelper {

    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTANCE = 50;
    private static final int DEFAULT_PRICE = 1_250;
    private static final double CHECKING_DISTANCE = 5.0;
    private static final int PRICE_PER_CHECKING_DISTANCE = 100;

    @Override
    public boolean canSupport(int distance) {
        return distance > MIN_DISTANCE && distance <= MAX_DISTANCE;
    }

    @Override
    public int calculatePrice(int distance, List<Line> lines) {
        int linePrice = lines.stream().mapToInt(Line::getPrice).sum();
        int distancePrice = (int) Math.ceil((distance - MIN_DISTANCE) / CHECKING_DISTANCE)
            * PRICE_PER_CHECKING_DISTANCE;

        return DEFAULT_PRICE + linePrice + distancePrice;
    }
}
