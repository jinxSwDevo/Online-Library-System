import React, { useState } from "react";
import { useNavigate } from "react-router";
import styled from "styled-components";
import Navbar from "../../components/Navbar";
import { TextField, Button, Select, MenuItem } from "@mui/material";
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

const genres = [
    "Fiction",
    "Non-fiction",
    "Science Fiction",
    "Fantasy",
    "Mystery",
    "Romance",
    "Thriller",
    "Horror",
    "Biography",
    "History",
    "Self-help",
    "Cooking",
    "Poetry",
    "Graphic Novel",
    "Young Adult",
    "Children's",
    "Art",
    "Science",
    "Travel",
    "Philosophy",
    "Religion",
    "Business",
    "Technology",
];

function AddBook() {
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [genre, setGenre] = useState('');
    const [rate, setRate] = useState(0);
    const [poster, setPoster] = useState(null); // Store poster file object
    const navigate = useNavigate();

    const formSubmit = async (e) => {
        e.preventDefault();

        // Check if all required fields are filled
        if (!title || !author || !genre || rate === 0 || !poster) {
            Swal.fire({
                title: `Please fill out all the fields.`,
                icon: 'error'
            });
            return;
        }

        // Check if rate is within the valid range (0 to 10)
        if (rate < 0 || rate > 10) {
            Swal.fire({
                title: `Rate must be between 0 and 10.`,
                icon: 'error'
            });
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('author', author);
        formData.append('genre', genre);
        formData.append('rate', rate);
        formData.append('poster', poster); // Append file object

        try {
            const response = await fetch('http://localhost:8081/api/books/add', {
                method: "POST",
                body: formData,
            });

            if (!response.ok) {
                Swal.fire({
                    title: `the title is used before please change`,
                    icon: 'error'
                });
                return;
            }

            Swal.fire({
                title: `add success`,
                icon: 'success'
            });
            navigate('/viewbook');
        } catch (error) {
            console.error('Error adding book:', error);
            Swal.fire({
                title: `Error adding book: ${error.message}`,
                icon: 'error'
            });
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
                <h1>Add Book</h1>
                <form onSubmit={formSubmit}>
                    <FormLabel htmlFor="title">Title</FormLabel>
                    <FormControl
                        type="text"
                        id="title"
                        placeholder="Enter the title of the book."
                        onChange={(e) => setTitle(e.target.value)}
                    />
                    <FormLabel htmlFor="author">Author</FormLabel>
                    <FormControl
                        type="text"
                        id="author"
                        placeholder="Enter the author of the book."
                        onChange={(e) => setAuthor(e.target.value)}
                    />
                    <FormLabel htmlFor="genre">Genre</FormLabel>
                    <Select
                        id="genre"
                        value={genre}
                        onChange={(e) => setGenre(e.target.value)}
                        fullWidth
                        displayEmpty
                    >
                        <MenuItem value="" disabled>
                            Select Genre
                        </MenuItem>
                        {genres.map((genreOption) => (
                            <MenuItem key={genreOption} value={genreOption}>
                                {genreOption}
                            </MenuItem>
                        ))}
                    </Select>
                    <FormLabel htmlFor="rate">Rate (Max 10)</FormLabel>
                    <FormControl
                        type="number"
                        id="rate"
                        placeholder="Enter the rate of the book."
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
                    <SubmitButton type="submit">Add Book</SubmitButton>
                </form>
            </Container>
        </>
    );
}

export default AddBook;
