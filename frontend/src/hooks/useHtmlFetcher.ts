export async function fetchHtmlFrontend(url: string): Promise<string> {
    const res = await fetch(url, {
        method: "GET",
        mode: "cors"
    });

    const text = await res.text();
    return text;
}
