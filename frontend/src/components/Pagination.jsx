export default function Pagination({ currentPage, totalPages, onPageChange }) {
  return (
    <div className="d-flex justify-content-center gap-2 mt-4">

      <button
        className="btn btn-outline-primary"
        disabled={currentPage === 0}
        onClick={() => onPageChange(currentPage - 1)}
      >
        Prev
      </button>

      <span className="align-self-center">
        Page {currentPage + 1} of {totalPages}
      </span>

      <button
        className="btn btn-outline-primary"
        disabled={currentPage + 1 >= totalPages}
        onClick={() => onPageChange(currentPage + 1)}
      >
        Next
      </button>

    </div>
  );
}
