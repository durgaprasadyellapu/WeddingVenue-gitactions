
import { useEffect, useState } from "react";
import "./App.css";

const API = "http://localhost:8081/wedding";

function App() {
  const emptyForm = {
    venueName: "",
    city: "",
    guestCapacity: "",
    rentalCost: ""
  };

  const [venue, setVenue] = useState({ ...emptyForm });
  const [venues, setVenues] = useState([]);
  const [venueId, setVenueId] = useState("");
  const [result, setResult] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);
  const [adding, setAdding] = useState(false);

  async function apiRequest(url, options = {}) {
    const response = await fetch(url, options);
    const text = await response.text();

    let data = text;
    try {
      data = text ? JSON.parse(text) : null;
    } catch {}

    if (!response.ok) {
      throw new Error(
        typeof data === "string" ? data : JSON.stringify(data)
      );
    }

    return data;
  }

  async function loadVenues() {
    setLoading(true);

    try {
      const data = await apiRequest(`${API}/displayall`);
      setVenues(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Load venues error:", err);
      setMessage(`Cannot load venues: ${err.message}`);
      setError(true);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadVenues();
  }, []);

  function handleChange(event) {
    setVenue({
      ...venue,
      [event.target.name]: event.target.value
    });
  }

  async function addVenue(event) {
    event.preventDefault();
    setAdding(true);
    setMessage("Adding venue...");
    setError(false);

    const payload = {
      venueName: venue.venueName.trim(),
      city: venue.city.trim(),
      guestCapacity: Number(venue.guestCapacity),
      rentalCost: Number(venue.rentalCost)
    };

    try {
      const saved = await apiRequest(`${API}/add`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      });

      setResult(saved);
      setMessage("Wedding venue added successfully!");
      setVenue({ ...emptyForm });
      await loadVenues();
    } catch (err) {
      console.error("Add venue error:", err);
      setMessage(`Failed to add venue: ${err.message}`);
      setError(true);
    } finally {
      setAdding(false);
    }
  }

  async function searchVenue(event) {
    event.preventDefault();
    setMessage("Searching...");
    setError(false);
    setResult(null);

    try {
      const data = await apiRequest(
        `${API}/display?id=${encodeURIComponent(venueId)}`
      );
      setResult(data);
      setMessage("Wedding venue found!");
    } catch (err) {
      setMessage(`Search failed: ${err.message}`);
      setError(true);
    }
  }

  const keys = [
    ...new Set(venues.flatMap(item => Object.keys(item || {})))
  ];

  return (
    <div className="app">
      <header className="hero">
        <p className="subtitle">WEDDING VENUE MANAGEMENT</p>
        <h1>Make Room for Forever</h1>
        <p>Find and manage the perfect place for your special day.</p>
        <h3>WeddingVenue-2400031608</h3>
      </header>

      <main className="container">
        {message && (
          <div className={`message ${error ? "error" : "success"}`}>
            {message}
          </div>
        )}

        <section className="card">
          <h2>Add Wedding Venue</h2>

          <form onSubmit={addVenue}>
            <label htmlFor="venueName">Venue Name</label>
            <input
              id="venueName"
              name="venueName"
              value={venue.venueName}
              onChange={handleChange}
              placeholder="Enter venue name"
              required
            />

            <label htmlFor="city">City</label>
            <input
              id="city"
              name="city"
              value={venue.city}
              onChange={handleChange}
              placeholder="Enter city"
              required
            />

            <label htmlFor="guestCapacity">Guest Capacity</label>
            <input
              id="guestCapacity"
              name="guestCapacity"
              type="number"
              min="1"
              value={venue.guestCapacity}
              onChange={handleChange}
              placeholder="Enter guest capacity"
              required
            />

            <label htmlFor="rentalCost">Rental Cost</label>
            <input
              id="rentalCost"
              name="rentalCost"
              type="number"
              min="0"
              step="0.01"
              value={venue.rentalCost}
              onChange={handleChange}
              placeholder="Enter rental cost"
              required
            />

            <button type="submit" disabled={adding}>
              {adding ? "Adding..." : "Add Venue"}
            </button>
          </form>
        </section>

        <section className="card">
          <h2>Search Venue by ID</h2>

          <form onSubmit={searchVenue}>
            <label htmlFor="venueId">Venue ID</label>
            <input
              id="venueId"
              type="number"
              min="1"
              value={venueId}
              onChange={event => setVenueId(event.target.value)}
              placeholder="Enter venue ID"
              required
            />

            <button type="submit">Search Venue</button>
          </form>

          {result && <pre>{JSON.stringify(result, null, 2)}</pre>}
        </section>

        <section className="card">
          <div className="heading">
            <h2>All Wedding Venues</h2>
            <button
              type="button"
              onClick={loadVenues}
              disabled={loading}
            >
              {loading ? "Loading..." : "Refresh"}
            </button>
          </div>

          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  {keys.length > 0
                    ? keys.map(key => <th key={key}>{key}</th>)
                    : <th>Venue Details</th>}
                </tr>
              </thead>

              <tbody>
                {venues.map((item, index) => (
                  <tr key={item.id ?? index}>
                    {keys.map(key => (
                      <td key={key}>
                        {item[key] == null
                          ? "-"
                          : typeof item[key] === "object"
                            ? JSON.stringify(item[key])
                            : String(item[key])}
                      </td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>

            {venues.length === 0 && (
              <p className="empty">
                {loading ? "Loading venues..." : "No wedding venues found."}
              </p>
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;
