import {DateTime} from "luxon";

export default class TimeConverter {
    static deadline(dateTime) {
        const now = DateTime.now();
        const date = DateTime.fromISO(dateTime);

        let result = `${date.toFormat('dd')} ${date.toFormat('LLL')}`;
        if (date.year !== now.year) result += ` ${date.toFormat('yyyy')}`;

        return result;
    }

    static twoDeadlines(startDate, endDate) {
        return `${this.deadline(startDate)} - ${this.deadline(endDate)}`
    }
}