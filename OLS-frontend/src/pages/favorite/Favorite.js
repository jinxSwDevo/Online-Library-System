import React, { useState, useEffect } from 'react';
import Card from '@mui/joy/Card';
import CardContent from '@mui/joy/CardContent';
import Typography from '@mui/joy/Typography';
import Button from '@mui/joy/Button';
import AspectRatio from "@mui/joy/AspectRatio";
import Header from "../../components/Header";
import { useParams } from 'react-router-dom';

const Favorite = () => {
  function getFilenameFromPath(path) {
    const parts = path.split("\\");
    return parts[parts.length - 1];
};
  const [data, setData] = useState([]);
  const { userId } = useParams();

  useEffect(() => {
    fetchData();
  }, [userId]); // Fetch data when userId changes

  const fetchData = async () => {
    try {
      const response = await fetch(`https://localhost:7007/api/user/gellallthebookwiththid/${userId}`);
      if (!response.ok) {
        throw new Error('Failed to fetch data');
      }
      const result = await response.json();
      setData(result);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <>
      <Header />
      <div style={{ display: 'flex', justifyContent: 'center', paddingTop: '3rem', flexWrap: 'wrap' }}>
        {data.map((book, index) => (
          <Card key={index} sx={{ width: 320, margin: '2rem' }}>
            <AspectRatio minHeight="120px" maxHeight="200px">
              <img
               src={require(`../../images/${getFilenameFromPath(book.book.poster)}`)}
                alt={book.book.title}
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
              />
            </AspectRatio>
            <CardContent orientation="horizontal">
              <Typography level="body-xs">Title:</Typography>
              <Typography fontSize="lg" fontWeight="bold">
                {book.book.title}
              </Typography>
              {/* Check if book.acceptable is true or false */}
              {book.acceptable ? (
                <Button
                variant="solid"
              size="md"
              color="success"
              aria-label="Accepted"
              sx={{ ml: 'auto', alignSelf: 'center', fontWeight: 600 }}
              >
              Accepted
                </Button>
              ) : (
                <Button
                variant="solid"
                  size="md"
                  color="neutral"
                  aria-label="Waiting"
                  sx={{ ml: 'auto', alignSelf: 'center', fontWeight: 600 }}
                >
                  Waiting
            
                </Button>
              )}
            </CardContent>
          </Card>
        ))}
      </div>
    </>
  );
};
export default Favorite;
