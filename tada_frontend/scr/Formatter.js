
export const dateFormatter = new Intl.DateTimeFormat("de",{
    day: "numeric",
    month: "numeric",
    year: "numeric",
    hour: "2-digit",
    minute:"2-digit",
    second: "2-digit"
});
export const timeFormatter = new Intl.RelativeTimeFormat("en",{style: "long"})

const DIVISIONS = [
    { amount: 60, name: "seconds" },
    { amount: 60, name: "minutes" },
    { amount: 24, name: "hours" },
    { amount: 7, name: "days" },
    { amount: 4.34524, name: "weeks" },
    { amount: 12, name: "months" },
    { amount: Number.POSITIVE_INFINITY, name: "years" },
]
export function formatTimeAgo(date) {
    let duration = (date - new Date()) / 1000

    for (let i = 0; i < DIVISIONS.length; i++) {
        const division = DIVISIONS[i]
        if (Math.abs(duration) < division.amount) {
            return timeFormatter.format(Math.round(duration), division.name)
        }
        duration /= division.amount
    }
}



