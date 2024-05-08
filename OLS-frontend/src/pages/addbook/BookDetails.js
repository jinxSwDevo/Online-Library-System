import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

const Container = styled.div`
  max-width: 600px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const BookTitle = styled.h1`
  font-size: 24px;
  margin-bottom: 16px;
`;

const BookInfo = styled.p`
  font-size: 16px;
  margin-bottom: 8px;
`;

const PosterImage = styled.img`
  width: 100%;
  max-height: 400px;
  object-fit: contain;
  margin-bottom: 16px;
  border-radius: 8px;
`;

function BookDetails() {
  const { BookID } = useParams();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBookDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8081/api/books/${BookID}`);
        if (!response.ok) {
          throw new Error('Failed to fetch book details');
        }
        const bookData = await response.json();
        setBook(bookData); // Assuming the API returns the entire book object
      } catch (error) {
        console.error('Error fetching book details:', error);
      } finally {
        setLoading(false); // Set loading to false after fetching data (success or error)
      }
    };

    fetchBookDetails();
  }, [BookID]);

  if (loading) {
    return <Container>Loading...</Container>;
  }

  if (!book) {
    return <Container>Book not found.</Container>;
  }

  return (
    <Container>
      <BookTitle>{book.title}</BookTitle>
      {book.poster && (
        <PosterImage src={`../../images/${getFilenameFromPath(book.poster)}`} alt={book.title} />
      )}
      <BookInfo>Author: {book.author}</BookInfo>
      <BookInfo>Genre: {book.genre}</BookInfo>
      <BookInfo>Rate: {book.rate}</BookInfo>
    </Container>
  );
}

function getFilenameFromPath(path) {
  const parts = path.split("\\");
  return parts[parts.length - 1];
}

export default BookDetails;
