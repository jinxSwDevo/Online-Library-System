import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import AspectRatio from "@mui/joy/AspectRatio";
import Button from "@mui/joy/Button";
import Card from "@mui/joy/Card";
import CardContent from "@mui/joy/CardContent";
import Typography from "@mui/joy/Typography";
import Header from "../../components/Header";
import "./book.css"; // Import custom CSS file

function getFilenameFromPath(path) {
  const parts = path.split("\\");
  return parts[parts.length - 1];
}

const Books = () => {
  const [books, setBooks] = useState([]);
  const [filteredBooks, setFilteredBooks] = useState([]);
  const { userId } = useParams(); // Retrieve userId from URL params
  const [searchTerm, setSearchTerm] = useState("");
  const [filterOption, setFilterOption] = useState("title"); // Default filter option

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await fetch("http://localhost:8081/books/");

      if (!response.ok) {
        throw new Error("Failed to fetch books");
      }

      const data = await response.json();
      setBooks(data);
      setFilteredBooks(data);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  useEffect(() => {
    filterBooks();
  }, [searchTerm, filterOption, books]);

  const filterBooks = () => {
    const filtered = books.filter((book) =>
      book[filterOption].toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredBooks(filtered);
  };

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleFilterOptionChange = (event) => {
    setFilterOption(event.target.value);
    filterBooks();
  };

  return (
    <>
      <Header />
      <div className="books-container">
        <div className="search-filter-container">
          <input
            type="text"
            placeholder={`Search by ${filterOption}`}
            value={searchTerm}
            onChange={handleSearchChange}
            className="search-input"
          />
          <select
            value={filterOption}
            onChange={handleFilterOptionChange}
            className="filter-select"
          >
            <option value="title">Title</option>
            <option value="author">Author</option>
            <option value="genre">Genre</option>
          </select>
        </div>
        <div className="cards-container">
          {filteredBooks.map((book, index) => (
            <Card key={index} className="book-card">
              <AspectRatio minHeight="120px" maxHeight="200px">
              {book.poster && (
                  <img
                    src={require(`../../images/8bdbd531-3d4b-42ac-a1b8-48dec60d87c5.jpg`)}
                    alt={book.poster}
                    className="book-image"
                  />
                )}
              </AspectRatio>
              <CardContent className="book-content">
                <Typography level="body-xs">{filterOption}:</Typography>
                <Typography fontSize="lg" fontWeight="bold">
                  {book[filterOption]}
                </Typography>
                <Button
                  variant="contained"
                  size="small"
                  color="primary"
                  className="view-button"
                >
                  <Link to={`/bookdetails/${book.id}`} className="view-link">
                    View
                  </Link>
                </Button>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </>
  );
};

export default Books;
