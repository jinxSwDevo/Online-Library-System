import React, { useEffect, useState } from 'react';
import Navbar from "../../components/Navbar";
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { Button } from '@mui/material';

const ViewBook = () => {
    const [books, setBooks] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        getAllBooks();
    }, []);

    const getAllBooks = () => {
        fetch('http://localhost:8081/api/books')
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Network response was not ok');
                }
                return res.json();
            })
            .then((data) => {
                if (Array.isArray(data)) {
                    setBooks(data);
                } else {
                    console.error('Invalid data format received:', data);
                }
            })
            .catch((error) => {
                console.error('Error fetching books:', error);
            });
    };

    const handleEdit = (book) => {
        // Redirect to the edit page with the book ID
        navigate(`/editbook/${book.id}`);
    };

    const deleteBook = (book) => {
        Swal.fire({
            title: `Are You Sure To Delete ${book.title}`,
            showCancelButton: true
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`http://localhost:8081/api/books/${book.id}`, {
                    method: "DELETE"
                })
                    .then((res) => {
                        if (!res.ok) {
                            throw new Error('Failed to delete book');
                        }
                        return res.json();
                    })
                    .then(() => {
                        getAllBooks(); // Refresh the book list after deletion
                    })
                    .catch((error) => {
                      getAllBooks();

                    });
            }
        });
    };

    return (
        <>
            <Navbar />
            <div className="container">
                <section className="main">
                    <section className="attendance">
                        <div className="attendance-list">
                            <Button>
                                <Link to={'/viewbook/add'}>Add Book</Link>
                            </Button>
                            <table className="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>Update</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {books.map((book) => (
                                        <tr key={book.id}>
                                            <td>{book.id}</td>
                                            <td>{book.title}</td>
                                            <td>{book.author}</td>
                                            <td>
                                                <Button
                                                    onClick={() => handleEdit(book)}
                                                >
                                                    Edit
                                                </Button>
                                            </td>
                                            <td>
                                                <Button
                                                    onClick={() => deleteBook(book)}
                                                >
                                                    Delete Book
                                                </Button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </section>
                </section>
            </div>
        </>
    );
};

export default ViewBook;
