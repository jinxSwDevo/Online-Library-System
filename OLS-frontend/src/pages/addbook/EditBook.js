import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router";
import styled from "styled-components";
import Navbar from "../../components/Navbar";
import { TextField, Button } from "@mui/material";
import Swal from 'sweetalert2';
const Container = styled.div`
    max-width: 600px;
    margin: auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const FormLabel = styled.label`
    font-weight: bold;
    margin-bottom: 8px;
`;

const FormControl = styled(TextField)`
    width: 100%;
    margin-bottom: 16px;
`;

const SubmitButton = styled(Button)`
    && {
        background-color: #007bff;
        color: #fff;
        padding: 12px 24px;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
    }

    &&:hover {
        background-color: #0056b3;
    }
`;

function EditBook() {
    const { BookID } = useParams();
    const navigate = useNavigate();

    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [genre, setGenre] = useState('');
    const [rate, setRate] = useState(0);
    const [poster, setPoster] = useState(null);

    useEffect(() => {
        const fetchBookData = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/books/${BookID}`);
                if (response.ok) {
                    const data = await response.json();
                    console.log('API response:', data);

                    // Set the fetched book data into state
                    setTitle(data.title || data.title);
                    setAuthor(data.author || data.author);
                    setGenre(data.genre || data.genre);
                    setRate(data.rate || data.rate);
                    setPoster(data.poster || data.poster);
                } else {
                    throw new Error('Network response was not ok.');
                }
            } catch (error) {
                console.error('Error fetching book data:', error);
            }
        };

        fetchBookData();
    }, [BookID]);

    const formSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('title', title);
        formData.append('author', author);
        formData.append('genre', genre);
        formData.append('rate', rate);
        if (poster) {
            formData.append('poster', poster); // Append file object if it exists
        }

        try {
            const response = await fetch(`http://localhost:8081/api/books/${BookID}`, {
                method: "PUT",
                body: formData,
            });

            if (!response.ok) {
                throw new Error('Failed to update book');
            }
            Swal.fire({
                title: `  update success`
              });
            navigate('/viewbook');
        } catch (error) {
            console.error('Error updating book:', error);
            // Handle error without navigating away
            // You can display an error message or take other actions
        }
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setPoster(file);
    };

    return (
        <>
            <Navbar />
            <Container>
                <h1>Edit Book</h1>
                <form onSubmit={formSubmit}>
                    <FormLabel htmlFor="title">Title</FormLabel>
                    <FormControl
                        type="text"
                        id="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />
                    <FormLabel htmlFor="author">Author</FormLabel>
                    <FormControl
                        type="text"
                        id="author"
                        value={author}
                        onChange={(e) => setAuthor(e.target.value)}
                    />
                    <FormLabel htmlFor="genre">Genre</FormLabel>
                    <FormControl
                        type="text"
                        id="genre"
                        value={genre}
                        onChange={(e) => setGenre(e.target.value)}
                    />
                    <FormLabel htmlFor="rate">Rate (Max 10)</FormLabel>
                    <FormControl
                        type="number"
                        id="rate"
                        value={rate}
                        min="0"
                        max="10"
                        onChange={(e) => setRate(parseInt(e.target.value))}
                    />
                    <FormLabel htmlFor="poster">Poster</FormLabel>
                    <input
                        type="file"
                        id="poster"
                        accept="image/*"
                        onChange={handleFileChange}
                    />
                    <SubmitButton type="submit">Save Changes</SubmitButton>
                </form>
            </Container>
        </>
    );
}

export default EditBook;
