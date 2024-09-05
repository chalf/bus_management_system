// Style.js
const styles = {
  halfRoundButton: {
    position: "fixed",
    top: "50%",
    left: 0,
    transform: "translateY(-50%)",
    width: "50px",
    height: "50px",
    backgroundColor: "#f3f3f3",
    borderRadius: "0 25px 25px 0",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    color: "black",
    fontSize: "24px",
    cursor: "pointer",
    transition: "background-color 0.3s, left 0.3s",
    zIndex: 1000,
  },
  halfRoundButtonHover: {
    backgroundColor: "#0056b3",
  },
  halfRoundButtonOpen: {
    left: "40%",
  },
  offCanvasMenu: {
    position: "fixed",
    top: "55px",
    left: "-40%",
    width: "40%",
    height: "calc(100% - 55px)",
    backgroundColor: "#f3f3f3",
    color: "black",
    padding: "20px",
    transition: "left 0.3s",
    zIndex: 999,
    overflowY: 'auto',
  },
  offCanvasMenuOpen: {
    left: 0,
    transform: 'translateX(0)',
  },
  title: {
    fontSize: "18px",
    fontWeight: "bold",
    marginBottom: "15px",
  },
  searchContainer: {
    display: "flex",
    flexDirection: "column",
    gap: "10px",
  },
  searchWrapper: {
    display: "flex",
    alignItems: "center",
    padding: "5px",
  },
   searchInput: {
    width: "100%",
    padding: "10px",
    border: "1px solid #ccc", // Customize the border
    borderRadius: "4px", // Add some border-radius if you like
    outline: "none", // Remove the default outline
    boxShadow: "none", // Remove the default box-shadow
  },
  searchButton: {
    backgroundColor: "transparent",
    border: "none",
    cursor: "pointer",
    fontSize: "16px",
    color: "#007bff",
  },
  searchButtonHover: {
  },
  formContainer: {
    padding: '20px',
    border: '1px solid #ccc',
    borderRadius: '8px',
    backgroundColor: '#fff',
  },
  customTable: {
    border: 'none', /* Remove borders from the table */
  },
  customTableRow: {
    backgroundColor: '#f8f9fa', /* Light gray background for the first row */
  },
  avatarContainer: {
    position: 'relative',
    display: 'inline-block',
    width: '150px',
    height: '150px',
  },
  avatarImage: {
    width: '100%',
    height: '100%',
    borderRadius: '50%',
  },
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    background: 'rgba(0, 0, 0, 0.5)',
    color: 'white',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    opacity: 0,
    transition: 'opacity 0.3s',
    borderRadius: '50%',
  },
  avatarContainerHover: {
    '&:hover .overlay': {
      opacity: 1,
    },
  },
  button: {
    position: 'absolute',
    bottom: '10px',
    right: '10px',
    background: 'rgba(0, 0, 0, 0.7)',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    padding: '5px 10px',
    cursor: 'pointer',
  },
  routeDetailsBox: {
    maxHeight: '80vh', // Limit the height of the route details box
    overflowY: 'auto', // Make it scrollable
    padding: '10px',
    marginBottom: '20px',
  },
  moreVertIcon: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    cursor: "pointer",
    margin: "10px 0", // Margin to create space around the icon
  },
  boldLineHover: {
    "&:hover": {
      borderBottom: "2px solid #333", // Make the line bold when hovered
    },
  },
  line: {
    width: "100%",
    borderBottom: "1px solid #ccc",
  },
  icon: {
    fontSize: "16px", // Decrease the size of the icons
    marginRight: "10px", // Add some spacing between the icon and the text
  },
  moreVertIcon: {
    fontSize: "16px",
    color: "#555",
  },
};

export default styles;
