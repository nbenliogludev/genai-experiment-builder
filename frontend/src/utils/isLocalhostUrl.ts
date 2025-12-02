export function isLocalhostUrl(url: string): boolean {
    try {
        const u = new URL(url);
        return (
            u.hostname === "localhost" ||
            u.hostname === "127.0.0.1" ||
            u.hostname === "0.0.0.0"
        );
    } catch {
        return false;
    }
}
